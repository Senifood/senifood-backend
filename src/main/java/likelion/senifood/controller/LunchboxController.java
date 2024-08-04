package likelion.senifood.controller;

import likelion.senifood.common.CommonResponse;
import likelion.senifood.dto.LunchboxDTO;
import likelion.senifood.entity.Lunchbox;
import likelion.senifood.entity.Subscription;
import likelion.senifood.service.LunchboxService;
import likelion.senifood.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lunchbox")
@RequiredArgsConstructor
public class LunchboxController {

    private final LunchboxService lunchboxService;
    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<List<LunchboxDTO>> getAllLunchboxes() {
        List<LunchboxDTO> lunchboxes = lunchboxService.getAllLunchboxes();
        return ResponseEntity.ok(lunchboxes);
    }

    @PostMapping("subscribe/{userId}")
    public ResponseEntity<String> subscribeToLunchbox
            (@PathVariable("userId") String userId,
             @RequestBody Map<String, Long> requestBody) {

        Long lunchboxId = requestBody.get("lunchbox_id");
        if(lunchboxId == null) {
            return new ResponseEntity<>("lunchbox_id is required", HttpStatus.BAD_REQUEST);
        }

        String startingDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Subscription subscription = new Subscription();
        subscription.setUserId(userId);
        subscription.setLunchboxId(lunchboxId);
        subscription.setStartingDate(startingDate);

        subscriptionService.saveSubscription(subscription);

        return new ResponseEntity<>("Successful subscription", HttpStatus.OK);
    }
}
