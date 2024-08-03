package likelion.senifood.controller;

import likelion.senifood.dto.LunchboxDTO;
import likelion.senifood.entity.Lunchbox;
import likelion.senifood.service.LunchboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lunchbox")
@RequiredArgsConstructor
public class LunchboxController {

    private final LunchboxService lunchboxService;

    @GetMapping
    public ResponseEntity<List<LunchboxDTO>> getAllLunchboxes() {
        List<LunchboxDTO> lunchboxes = lunchboxService.getAllLunchboxes();
        return ResponseEntity.ok(lunchboxes);
    }
}
