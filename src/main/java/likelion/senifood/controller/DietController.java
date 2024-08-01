package likelion.senifood.controller;

import likelion.senifood.dto.HealthInfoRequest;
import likelion.senifood.entity.Diet;
import likelion.senifood.service.ChatGptService;
import likelion.senifood.service.DietService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diet")
public class DietController {

    private final DietService dietService;
    private final ChatGptService chatGptService;

    public DietController(DietService dietService, ChatGptService chatGptService) {
        this.dietService = dietService;
        this.chatGptService = chatGptService;
    }

    @PostMapping("/generate")
    public ResponseEntity<Diet> generateDiet(@RequestBody HealthInfoRequest healthInfoRequest) {
        // 건강 정보와 알레르기 정보를 바탕으로 질문 생성
        StringBuilder query = new StringBuilder("Please suggest a diet plan for a person with the following health conditions and allergies:\n");

        if (healthInfoRequest.getDiseases() != null && !healthInfoRequest.getDiseases().isEmpty()) {
            query.append("Diseases: ").append(String.join(", ", healthInfoRequest.getDiseases())).append("\n");
        }
        if (healthInfoRequest.getAllergies() != null && !healthInfoRequest.getAllergies().isEmpty()) {
            query.append("Allergies: ").append(String.join(", ", healthInfoRequest.getAllergies())).append("\n");
        }

        query.append("Please provide the following details:\n");
        query.append("- Diet name\n");
        query.append("- Nutritional content\n");
        query.append("- Benefits\n");
        query.append("- Recipe URL\n");
        query.append("- Image URL");

        // ChatGPT API 호출
        String chatGptResponse = chatGptService.askChatGpt(query.toString());

        // 응답을 바탕으로 Diet 객체 생성
        Diet diet = new Diet();
        // 여기서 chatGptResponse를 파싱하여 diet 객체의 속성을 설정합니다.
        diet.setDietTitle("Example Diet Title");
        diet.setDietBenefit("Example Benefits");
        diet.setDietNutrition("Example Nutritional Content");
        diet.setDietRecipeURL("http://example.com/recipe");
        diet.setDietImageURL("http://example.com/image");

        // Diet 객체 저장
        Diet savedDiet = dietService.saveDiet(diet);

        return new ResponseEntity<>(savedDiet, HttpStatus.CREATED);
    }
}
