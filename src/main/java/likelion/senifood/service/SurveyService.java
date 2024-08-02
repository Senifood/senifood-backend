package likelion.senifood.service;

import likelion.senifood.entity.Survey;
import likelion.senifood.repository.SurveyRepository;
import org.springframework.stereotype.Service;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public Survey saveSurveyQuestions(Survey survey) {
        System.out.println("Saving survey: " + survey.toString());
        return surveyRepository.save(survey);
    }

    // 최신 설문지 질문 조회
    public Survey getLatestSurveyQuestions() {
        return surveyRepository.findTopByOrderBySurveyIdDesc();
    }
}
