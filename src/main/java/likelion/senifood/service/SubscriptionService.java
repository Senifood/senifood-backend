package likelion.senifood.service;

import likelion.senifood.common.CommonResponse;
import likelion.senifood.dto.SubscriptionResponseDTO;
import likelion.senifood.entity.Lunchbox;
import likelion.senifood.entity.Subscription;
import likelion.senifood.entity.SubscriptionId;
import likelion.senifood.entity.User;
import likelion.senifood.repository.LunchboxRepository;
import likelion.senifood.repository.SubscriptionRepository;
import likelion.senifood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final UserService userService;
    private final LunchboxService lunchboxService;
    private final SubscriptionRepository subscriptionRepository;

    public void saveSubscription(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public void deleteSubscription(String userId, Long lunchboxId) {
        SubscriptionId id = new SubscriptionId();
        id.setUserId(userId);
        id.setLunchboxId(lunchboxId);

        if (!subscriptionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "구독 중이지 않은 도시락입니다.");
        }

        subscriptionRepository.deleteById(id);
        lunchboxService.updateSubscriptionStatus(lunchboxId, false);
    }

    public List<SubscriptionResponseDTO> getSubscriptionList(String userId) {
        List<Subscription> subscriptionList = subscriptionRepository.findByUserId(userId);
        return subscriptionList.stream()
                .map(subscription -> {
                    Lunchbox lunchbox = lunchboxService.findById(subscription.getLunchboxId()).orElse(null);
                    if (lunchbox == null) {
                        return null;
                    }
                    LocalDate startingDate = LocalDate.parse(subscription.getStartingDate(), DateTimeFormatter.BASIC_ISO_DATE);
                    LocalDate expirationDate = startingDate.plusMonths(1); // assuming a one-month subscription for the example
                    return new SubscriptionResponseDTO(
                            subscription.getStartingDate(),
                            lunchbox.getLunchbox_id(),
                            lunchbox.getLunchbox_title(),
                            lunchbox.getLunchbox_imageURL(),
                            lunchbox.getLunchbox_price(),
                            expirationDate.format(DateTimeFormatter.BASIC_ISO_DATE)
                    );
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }
}
