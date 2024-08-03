package likelion.senifood.controller;

import likelion.senifood.common.CommonResponse;
import likelion.senifood.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lunchbox/subscribe")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/{userId}/{lunchboxId}")
    public ResponseEntity<CommonResponse> subscribeToLunchbox
            (@PathVariable("userId") String userId, @PathVariable("lunchboxId") Long lunchboxId) {

        CommonResponse response = subscriptionService.subscribeToLunchbox(userId, lunchboxId);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
