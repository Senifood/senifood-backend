package likelion.senifood.service;

import likelion.senifood.entity.Diet;
import likelion.senifood.repository.DietRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DietService {

    private final DietRepository dietRepository;

    public DietService(DietRepository dietRepository) {
        this.dietRepository = dietRepository;
    }

    public Optional<Diet> findById(Integer dietId) {
        return dietRepository.findById(dietId);
    }

    public Diet saveDiet(Diet diet) {
        return dietRepository.save(diet);
    }
}