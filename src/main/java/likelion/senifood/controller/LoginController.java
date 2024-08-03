package likelion.senifood.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import likelion.senifood.dto.LoginRequest;
import likelion.senifood.entity.User;
import likelion.senifood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByPhoneNumber(loginRequest.getPhoneNumber());

        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(user); // 로그인 성공
        }
        else {
            return ResponseEntity.badRequest()
                    .body("400 Bad Request: 요청 본문이 유효하지 않음"); // 로그인 실패
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();  // 세션 무효화
        }

        // 로그아웃 성공
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Logged out successfully.");

        return ResponseEntity.ok(response);
    }
}
