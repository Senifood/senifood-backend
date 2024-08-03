package likelion.senifood.controller;

import likelion.senifood.entity.Diet;
import likelion.senifood.entity.UserDietLike;
import likelion.senifood.entity.UserSurveyResponse;
import likelion.senifood.service.ChatGptService;
import likelion.senifood.service.DietService;
import likelion.senifood.service.UserDietLikeService;
import likelion.senifood.service.UserSurveyResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/diet")
public class DietController {

    private final DietService dietService;
    private final ChatGptService chatGptService;
    private final UserSurveyResponseService userSurveyResponseService;
    private final UserDietLikeService userDietLikeService;

    public DietController(DietService dietService, ChatGptService chatGptService, UserSurveyResponseService userSurveyResponseService, UserDietLikeService userDietLikeService) {
        this.dietService = dietService;
        this.chatGptService = chatGptService;
        this.userSurveyResponseService = userSurveyResponseService;
        this.userDietLikeService = userDietLikeService;
    }

    @PostMapping
    public ResponseEntity<Diet> generateDiet(@RequestBody Map<String, String> requestBody) {
        // 요청 바디에서 userId를 가져옴
        String userId = requestBody.get("user_id");

        // 유저 ID를 사용하여 설문 응답 가져오기
        List<UserSurveyResponse> userResponses = userSurveyResponseService.getUserResponses(userId);
        System.out.println(userResponses);

        // 질병, 알레르기, 복용 중인 약물 리스트 생성
        List<String> diseases = new ArrayList<>();
        List<String> allergies = new ArrayList<>();
        List<String> medications = new ArrayList<>();

        // 응답에서 질병, 알레르기, 약물 정보를 추출 (구체적인 로직은 상황에 맞게 수정)
        for (UserSurveyResponse response : userResponses) {
            System.out.println(response.getAnswer_1());
            if (response.getAnswer_1() != null) {
                diseases.add(response.getAnswer_1());  // 가정: answer1이 질병 정보일 경우
            }
            System.out.println(response.getAnswer_2());
            if (response.getAnswer_2() != null) {
                allergies.add(response.getAnswer_2());  // 가정: answer2이 알레르기 정보일 경우
            }
            System.out.println(response.getAnswer_3());
            if (response.getAnswer_3() != null) {
                medications.add(response.getAnswer_3());  // 가정: answer3이 복용 중인 약물일 경우
            }
        }

        // ChatGPT API에 전달할 질문 생성
        StringBuilder query = new StringBuilder("Please suggest a food for a person with the following health conditions, allergies, and medications:\n");

        if (!diseases.isEmpty()) {
            query.append("Diseases: ").append(String.join(", ", diseases)).append("\n");
        }
        if (!allergies.isEmpty()) {
            query.append("Allergies: ").append(String.join(", ", allergies)).append("\n");
        }
        if (!medications.isEmpty()) {
            query.append("Medications: ").append(String.join(", ", medications)).append("\n");
        }

        query.append("Provide the details for only one food in the following format:\n\n");
        query.append("Diet Title: [Title]\n");
        query.append("Benefits: [Benefits]\n");
        query.append("Nutritional Content: [Nutritional Content]\n");
        query.append("Recipe URL: [URL]\n");
        query.append("Image URL: [URL]\n");

        // ChatGPT API 호출
        Map<String, String> dietInfo = chatGptService.askChatGpt(query.toString());

        // Diet 객체 생성 및 속성 설정
        Diet diet = new Diet();
        diet.setDietTitle(dietInfo.get("dietTitle"));
        diet.setDietBenefit(dietInfo.get("dietBenefit"));
        diet.setDietNutrition(dietInfo.get("dietNutrition"));
        diet.setDietRecipeURL(dietInfo.get("dietRecipeURL"));
        diet.setDietImageURL(dietInfo.get("dietImageURL"));

        // Diet 객체 저장
        Diet savedDiet = dietService.saveDiet(diet);

        return new ResponseEntity<>(savedDiet, HttpStatus.CREATED);
    }

    @PostMapping("/likes/{user_id}")
    public ResponseEntity<String> likeDiet(
            @PathVariable("user_id") String userId,
            @RequestParam("diet_id") Integer dietId) {

        UserDietLike userDietLike = new UserDietLike(userId, dietId);
        userDietLikeService.saveUserDietLike(userDietLike);
        return new ResponseEntity<>("Diet liked successfully", HttpStatus.CREATED);
    }

    // 특정 사용자가 식단 좋아요를 취소하는 엔드포인트
    @DeleteMapping("/likes/{user_id}")
    public ResponseEntity<String> unlikeDiet(
            @PathVariable("user_id") String userId,
            @RequestParam("diet_id") Integer dietId) {

        userDietLikeService.deleteUserDietLike(userId, dietId);
        return new ResponseEntity<>("Diet unliked successfully", HttpStatus.OK);
    }

}
