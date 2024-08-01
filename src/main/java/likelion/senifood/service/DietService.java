package likelion.senifood.service;

import likelion.senifood.entity.Diet;
import likelion.senifood.repository.DietRepository;
import org.springframework.stereotype.Service;

@Service
public class DietService {

    private final DietRepository dietRepository;

    public DietService(DietRepository dietRepository) {
        this.dietRepository = dietRepository;
    }

    public Diet saveDiet(Diet diet) {
        return dietRepository.save(diet);
    }
}