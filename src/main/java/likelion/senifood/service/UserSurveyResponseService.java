package likelion.senifood.service;

import likelion.senifood.entity.UserSurveyResponse;
import likelion.senifood.repository.UserSurveyResponseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSurveyResponseService {

    private final UserSurveyResponseRepository userSurveyResponseRepository;

    public UserSurveyResponseService(UserSurveyResponseRepository userSurveyResponseRepository) {
        this.userSurveyResponseRepository = userSurveyResponseRepository;
    }

    public UserSurveyResponse saveUserResponse(UserSurveyResponse response) {
        return userSurveyResponseRepository.save(response);
    }

    public List<UserSurveyResponse> getUserResponses(String userId) {
        return userSurveyResponseRepository.findByUserId(userId);
    }

    public boolean hasUserResponded(String userId) {
        return userSurveyResponseRepository.existsByUserId(userId);
    }
}
