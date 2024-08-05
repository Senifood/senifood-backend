package likelion.senifood.repository;

import likelion.senifood.entity.Lunchbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LunchboxRepository extends JpaRepository<Lunchbox, Long> {

}
