package likelion.senifood.service;

import jakarta.transaction.Transactional;
import likelion.senifood.common.CommonResponse;
import likelion.senifood.dto.HealthInfoRequest;
import likelion.senifood.entity.User;
import likelion.senifood.entity.UserSurveyResponse;
import likelion.senifood.repository.UserSurveyResponseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

}
