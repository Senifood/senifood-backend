package likelion.senifood.repository;

import likelion.senifood.entity.UserSurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSurveyResponseRepository extends JpaRepository<UserSurveyResponse, Integer> {
    List<UserSurveyResponse> findByUserId(String userId);
}
