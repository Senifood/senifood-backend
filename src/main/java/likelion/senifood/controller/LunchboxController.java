package likelion.senifood.controller;

import likelion.senifood.common.CommonResponse;
import likelion.senifood.dto.LunchboxDTO;
import likelion.senifood.dto.SubscriptionResponseDTO;
import likelion.senifood.entity.Lunchbox;
import likelion.senifood.entity.Subscription;
import likelion.senifood.repository.LunchboxRepository;
import likelion.senifood.repository.SubscriptionRepository;
import likelion.senifood.service.LunchboxService;
import likelion.senifood.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lunchbox")
@RequiredArgsConstructor
public class LunchboxController {

    private final LunchboxService lunchboxService;
    private final SubscriptionService subscriptionService;
    private final LunchboxRepository lunchboxRepository;
    private final SubscriptionRepository subscriptionRepository;

    @GetMapping
    public ResponseEntity<List<LunchboxDTO>> getAllLunchboxes() {
        List<LunchboxDTO> lunchboxes = lunchboxService.getAllLunchboxes();
        return ResponseEntity.ok(lunchboxes);
    }

    //조회
    @GetMapping("/{lunchboxId}")
    public List<Lunchbox> getLunchboxInfo (
            @PathVariable("lunchboxId") Long lunchboxId,
            @RequestBody Map<String, String> requestBody) {

        String userId = requestBody.get("user_id");

        List<Subscription> subscriptionList = subscriptionRepository.findByUserId(userId);

        return subscriptionList.stream()
                .map(subscription -> {
                    Lunchbox lunchbox = lunchboxRepository.findById(lunchboxId).orElse(null);
                    if (lunchbox == null) {
                        return null;
                    }
                    if(subscription.getLunchboxId().equals(lunchbox.getLunchbox_id())) {
                        return new Lunchbox (
                                lunchbox.getLunchbox_id(),
                                lunchbox.getLunchbox_title(),
                                lunchbox.getLunchbox_imageURL(),
                                lunchbox.getLunchbox_price(),
                                true,
                                lunchbox.getLunchbox_foods()
                        );
                    }else {
                        return new Lunchbox (
                                lunchbox.getLunchbox_id(),
                                lunchbox.getLunchbox_title(),
                                lunchbox.getLunchbox_imageURL(),
                                lunchbox.getLunchbox_price(),
                                false,
                                lunchbox.getLunchbox_foods()
                        );
                    }

                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }

    public List<SubscriptionResponseDTO> getSubscriptionList(String userId) {
        List<Subscription> subscriptionList = subscriptionRepository.findByUserId(userId);
        return subscriptionList.stream()
                .map(subscription -> {
                    Lunchbox lunchbox = lunchboxRepository.findById(subscription.getLunchboxId()).orElse(null);
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

    //구독
    @PostMapping("subscribe/{userId}/{lunchboxId}")
    public ResponseEntity<String> subscribeToLunchbox
            (@PathVariable("userId") String userId,
             @PathVariable("lunchboxId") Long lunchboxId) {


        String startingDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Subscription subscription = new Subscription();
        subscription.setUserId(userId);
        subscription.setLunchboxId(lunchboxId);
        subscription.setStartingDate(startingDate);

        subscriptionService.saveSubscription(subscription);
//        lunchboxService.updateSubscriptionStatus(lunchboxId, true);

        return new ResponseEntity<>("Successful subscription", HttpStatus.OK);
    }

    //구독 목록 확인
    @GetMapping("subscribe/{userId}")
    public ResponseEntity<List<SubscriptionResponseDTO>> getSubscriptions(@PathVariable("userId") String userId) {
        List<SubscriptionResponseDTO> subscriptions = subscriptionService.getSubscriptionList(userId);
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    //구독 취소
    @DeleteMapping("subscribe/{userId}/{lunchboxId}")
    public ResponseEntity<String> cancelSubscription
    (@PathVariable("userId") String userId,
     @PathVariable("lunchboxId") Long lunchboxId) {
        try {
            subscriptionService.deleteSubscription(userId, lunchboxId);
            return new ResponseEntity<>("Subscription deleted successfully", HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }

    }

}
