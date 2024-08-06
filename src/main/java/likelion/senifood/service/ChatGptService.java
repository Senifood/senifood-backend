package likelion.senifood.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatGptService {

    private final String apiUrl = "https://api.openai.com/v1/chat/completions";
    private final String dalleApiUrl = "https://api.openai.com/v1/images/generations";
    private final String apiKey = System.getenv("GPT_API_KEY");

    public Map<String, String> askChatGpt(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o");

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);
        requestBody.put("messages", List.of(message));
        requestBody.put("max_tokens", 512);
        requestBody.put("temperature", 0.7);
        requestBody.put("top_p", 1.0);
        requestBody.put("frequency_penalty", 0.5);
        requestBody.put("presence_penalty", 0.5);


        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // API 호출
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        Map<String, String> dietInfo = parseResponse(response.getBody());

        // DALL-E 이미지 생성 요청
        String imagePrompt = generateImagePrompt(dietInfo);
        String imageUrl = generateImage(imagePrompt);

        dietInfo.put("dietImageURL", imageUrl);

        return dietInfo;
    }

    private String generateImage(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("prompt", prompt);
        requestBody.put("n", 1); // 이미지 생성 개수
        requestBody.put("size", "512x512"); // 이미지 크기

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(dalleApiUrl, HttpMethod.POST, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String imageUrl = "";
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            imageUrl = rootNode.path("data").get(0).path("url").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageUrl;
    }

    public String generateImagePrompt(Map<String, String> dietInfo) {
        StringBuilder imagePrompt = new StringBuilder();

        imagePrompt.append("Generate an image of a dish titled '").append(dietInfo.get("dietTitle")).append("'. ");
        imagePrompt.append("The dish should have the following elements: ");
        imagePrompt.append("fresh and colorful ingredients, such as ").append(dietInfo.get("dietNutrition")).append(". ");
        imagePrompt.append("It should be presented in a modern, appetizing style. ");
        imagePrompt.append("The image should look vibrant and healthy, capturing the essence of a nutritious and well-balanced meal.");

        return imagePrompt.toString();
    }

    public Map<String, String> parseResponse(String responseBody) {
        Map<String, String> dietInfo = new HashMap<>();

        try {
            // ObjectMapper를 사용하여 JSON 응답을 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            System.out.println(rootNode);

            // 응답에서 'choices' 배열의 첫 번째 항목의 'message' 내 'content' 가져오기
            String content = rootNode.path("choices").get(0).path("message").path("content").asText();
            System.out.println(content);

            String[] lines = content.split("\n");
            System.out.println(lines);
            for (String line : lines) {
                if (line.startsWith("Diet Title:")) {
                    dietInfo.put("dietTitle", line.replace("Diet Title:", "").trim());
                } else if (line.startsWith("Benefits:")) {
                    dietInfo.put("dietBenefit", line.replace("Benefits:", "").trim());
                } else if (line.startsWith("Nutritional Content:")) {
                    dietInfo.put("dietNutrition", line.replace("Nutritional Content:", "").trim());
                } else if (line.startsWith("Recipe URL:")) {
                    dietInfo.put("dietRecipeURL", line.replace("Recipe URL:", "").trim());
                } else if (line.startsWith("Image URL:")) {
                    dietInfo.put("dietImageURL", line.replace("Image URL:", "").trim());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dietInfo;
    }

}
