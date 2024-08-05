package likelion.senifood.service;

import likelion.senifood.dto.LunchboxDTO;
import likelion.senifood.entity.Lunchbox;
import likelion.senifood.repository.LunchboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LunchboxService {

    private final LunchboxRepository lunchboxRepository;

    public List<LunchboxDTO> getAllLunchboxes() {
        List<Lunchbox> lunchboxes = lunchboxRepository.findAll();
        return lunchboxes.stream()
                .map(lunchbox -> new LunchboxDTO(
                        lunchbox.getLunchbox_id(),
                        lunchbox.getLunchbox_title(),
                        lunchbox.getLunchbox_imageURL(),
                        lunchbox.getLunchbox_price()
                ))
                .collect(Collectors.toList());
    }

    public Optional<Lunchbox> findById(Long lunchboxId) { return lunchboxRepository.findById(lunchboxId); }

    public Lunchbox getLunchboxDetails(Long lunchboxId) {
        return lunchboxRepository.findById(lunchboxId)
                .orElseThrow(() -> new RuntimeException("도시락을 찾을 수 없습니다."));
    }

    public void updateSubscriptionStatus(Long lunchboxId, boolean isSubscribed) {
        Lunchbox lunchbox = getLunchboxDetails(lunchboxId);
        lunchbox.setIs_subscribed(isSubscribed);
        lunchboxRepository.save(lunchbox);
    }
}
