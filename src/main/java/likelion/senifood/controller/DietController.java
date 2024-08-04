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

    // 사용자의 건강 정보를 바탕으로 식단을 생성하는 엔드포인트
    @PostMapping
    public ResponseEntity<Diet> generateDiet(@RequestBody Map<String, String> requestBody) {

        String userId = requestBody.get("user_id");

        List<UserSurveyResponse> userResponses = userSurveyResponseService.getUserResponses(userId);
        System.out.println(userResponses);

        List<String> diseases = new ArrayList<>();
        List<String> allergies = new ArrayList<>();
        List<String> medications = new ArrayList<>();

        for (UserSurveyResponse response : userResponses) {
            System.out.println(response.getAnswer_1());
            if (response.getAnswer_1() != null) {
                diseases.add(response.getAnswer_1());
            }
            System.out.println(response.getAnswer_2());
            if (response.getAnswer_2() != null) {
                allergies.add(response.getAnswer_2());
            }
            System.out.println(response.getAnswer_3());
            if (response.getAnswer_3() != null) {
                medications.add(response.getAnswer_3());
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
        query.append("Benefits: [Please provide detailed information about the health benefits, including specific advantages for different conditions, any supporting scientific evidence, and how it contributes to overall well-being.]\n");
        query.append("Nutritional Content: [Provide a comprehensive breakdown of the nutritional content, including macronutrients (such as carbohydrates, proteins, fats), micronutrients (like vitamins and minerals), calorie count, and any other relevant nutritional information.]\n");
        query.append("Recipe URL: [Please provide a recipe URL that is as close to a real, existing recipe as possible.]\n");
        query.append("Image URL: [URL]\n");
        query.append("Please provide the response in Korean.\n");

        // ChatGPT API 호출
        Map<String, String> dietInfo = chatGptService.askChatGpt(query.toString());

        Diet diet = new Diet();
        diet.setDietTitle(dietInfo.get("dietTitle"));
        diet.setDietBenefit(dietInfo.get("dietBenefit"));
        diet.setDietNutrition(dietInfo.get("dietNutrition"));
        diet.setDietRecipeURL(dietInfo.get("dietRecipeURL"));
        diet.setDietImageURL(dietInfo.get("dietImageURL"));

        Diet savedDiet = dietService.saveDiet(diet);

        return new ResponseEntity<>(savedDiet, HttpStatus.CREATED);
    }

    // 특정 사용자가 식단 좋아요를 추가하는 엔드포인트
    @PostMapping("/likes/{user_id}")
    public ResponseEntity<String> likeDiet(
            @PathVariable("user_id") String userId,
            @RequestBody Map<String, Integer> requestBody) {

        Integer dietId = requestBody.get("diet_id");
        if (dietId == null) {
            return new ResponseEntity<>("diet_id is required", HttpStatus.BAD_REQUEST);
        }

        UserDietLike userDietLike = new UserDietLike();
        userDietLike.setUserId(userId);
        userDietLike.setDietId(dietId);
        userDietLikeService.saveUserDietLike(userDietLike);
        return new ResponseEntity<>("Diet liked successfully", HttpStatus.OK);
    }

    // 특정 사용자가 식단 좋아요를 취소하는 엔드포인트
    @DeleteMapping("/likes/{user_id}")
    public ResponseEntity<String> unlikeDiet(
            @PathVariable("user_id") String userId,
            @RequestBody Map<String, Integer> requestBody) {

        Integer dietId = requestBody.get("diet_id");
        if (dietId == null) {
            return new ResponseEntity<>("diet_id is required", HttpStatus.BAD_REQUEST);
        }

        userDietLikeService.deleteUserDietLike(userId, dietId);
        return new ResponseEntity<>("Diet unliked successfully", HttpStatus.OK);
    }

    // 특정 사용자가 좋아요한 식단 목록을 조회하는 엔드포인트
    @GetMapping("/likes/{user_id}")
    public ResponseEntity<List<Diet>> getUserLikedDiets(@PathVariable("user_id") String userId) {
        List<Diet> likedDiets = userDietLikeService.getUserLikedDiets(userId);
        return new ResponseEntity<>(likedDiets, HttpStatus.OK);
    }

}
