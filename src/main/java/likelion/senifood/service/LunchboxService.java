package likelion.senifood.service;

import likelion.senifood.entity.Lunchbox;
import likelion.senifood.repository.LunchboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LunchboxService {

    private final LunchboxRepository lunchboxRepository;

    public List<Lunchbox> getAllLunchboxes() {
        return lunchboxRepository.findAll();
    }
}
