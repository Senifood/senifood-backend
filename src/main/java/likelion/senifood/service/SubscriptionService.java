package likelion.senifood.service;

import likelion.senifood.common.CommonResponse;
import likelion.senifood.entity.Lunchbox;
import likelion.senifood.entity.Subscription;
import likelion.senifood.entity.User;
import likelion.senifood.repository.LunchboxRepository;
import likelion.senifood.repository.SubscriptionRepository;
import likelion.senifood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final UserRepository userRepository;
    private final LunchboxRepository lunchboxRepository;
    private final SubscriptionRepository subscriptionRepository;

    public CommonResponse subscribeToLunchbox(String userId, Long lunchboxId) {
        if (!userRepository.existsById(userId) || !lunchboxRepository.existsById(lunchboxId.longValue())) {
            return new CommonResponse(false, HttpStatus.NOT_FOUND, "사용자 또는 도시락을 찾을 수 없습니다.", null);
        }

        String startingDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Subscription subscription = new Subscription();
        subscription.setUserId(userId);
        subscription.setLunchboxId(lunchboxId);
        subscription.setStartingDate(startingDate);
        subscriptionRepository.save(subscription);

        return new CommonResponse(true, HttpStatus.OK, "구독 성공", subscription);
    }
}
