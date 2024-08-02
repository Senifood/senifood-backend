package likelion.senifood.controller;

import likelion.senifood.entity.Survey;
import likelion.senifood.entity.UserSurveyResponse;
import likelion.senifood.service.SurveyService;
import likelion.senifood.service.UserSurveyResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    private final SurveyService surveyService;
    private final UserSurveyResponseService userSurveyResponseService;

    public SurveyController(SurveyService surveyService, UserSurveyResponseService userSurveyResponseService) {
        this.surveyService = surveyService;
        this.userSurveyResponseService = userSurveyResponseService;
    }

    // 설문지 질문 등록
    @PostMapping("/questions")
    public ResponseEntity<Survey> createSurveyQuestions(@RequestBody Survey survey) {
        Survey savedSurvey = surveyService.saveSurveyQuestions(survey);
        return new ResponseEntity<>(savedSurvey, HttpStatus.CREATED);
    }

    // 설문지의 질문들 조회
    @GetMapping("/questions")
    public ResponseEntity<Survey> getSurveyQuestions() {
        Survey survey = surveyService.getLatestSurveyQuestions();
        return new ResponseEntity<>(survey, HttpStatus.OK);
    }

    // 설문지 질문에 대한 유저의 응답 저장
    @PostMapping("/{user_id}")
    public ResponseEntity<UserSurveyResponse> saveUserResponse(
            @PathVariable("user_id") String userId,
            @RequestBody Map<String, List<String>> responses) {

        String combinedAnswer1 = String.join(",", responses.get("answer1"));
        String combinedAnswer2 = String.join(",", responses.get("answer2"));
        String combinedAnswer3 = String.join(",", responses.get("answer3"));

        UserSurveyResponse response = new UserSurveyResponse();
        response.setUserId(userId);
        response.setAnswer_1(combinedAnswer1);
        response.setAnswer_2(combinedAnswer2);
        response.setAnswer_3(combinedAnswer3);

        UserSurveyResponse savedResponse = userSurveyResponseService.saveUserResponse(response);
        return new ResponseEntity<>(savedResponse, HttpStatus.CREATED);
    }


    // 특정 유저의 설문지 응답 조회
    @GetMapping("/responses/{user_id}")
    public ResponseEntity<Map<String, List<String>>> getUserResponses(@PathVariable("user_id") String userId) {
        UserSurveyResponse response = userSurveyResponseService.getUserResponses(userId).get(0);

        // ,로 구분된 문자열을 리스트로 변환
        List<String> answer1List = Arrays.asList(response.getAnswer_1().split(","));
        List<String> answer2List = Arrays.asList(response.getAnswer_2().split(","));
        List<String> answer3List = Arrays.asList(response.getAnswer_3().split(","));

        Map<String, List<String>> responses = new HashMap<>();
        responses.put("answer1", answer1List);
        responses.put("answer2", answer2List);
        responses.put("answer3", answer3List);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
