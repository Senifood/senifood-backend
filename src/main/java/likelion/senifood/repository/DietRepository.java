package likelion.senifood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import likelion.senifood.entity.Diet;

public interface DietRepository extends JpaRepository<Diet, Integer> { }