package likelion.senifood.controller;

import likelion.senifood.entity.Survey;
import likelion.senifood.entity.UserSurveyResponse;
import likelion.senifood.service.SurveyService;
import likelion.senifood.service.UserSurveyResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @RequestBody UserSurveyResponse response) {

        response.setUserId(userId);
        UserSurveyResponse savedResponse = userSurveyResponseService.saveUserResponse(response);
        return new ResponseEntity<>(savedResponse, HttpStatus.CREATED);
    }

    // 유저의 응답 조회
    @GetMapping("/responses/{user_id}")
    public ResponseEntity<List<UserSurveyResponse>> getUserResponses(@PathVariable("user_id") String userId) {
        List<UserSurveyResponse> responses = userSurveyResponseService.getUserResponses(userId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
