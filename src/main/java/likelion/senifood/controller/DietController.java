package likelion.senifood.controller;

import likelion.senifood.dto.HealthInfoRequest;
import likelion.senifood.entity.Diet;
import likelion.senifood.service.ChatGptService;
import likelion.senifood.service.DietService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

        query.append("Provide the details for only one diet in the following format:\n\n");
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
}
