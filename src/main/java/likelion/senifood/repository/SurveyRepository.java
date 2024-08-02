package likelion.senifood.repository;

import likelion.senifood.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {
    Survey findTopByOrderBySurveyIdDesc();
}
