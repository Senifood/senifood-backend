package likelion.senifood.controller;

import likelion.senifood.common.CommonResponse;
import likelion.senifood.dto.UserDTO;
import likelion.senifood.entity.User;
import likelion.senifood.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/join")
    public ResponseEntity<CommonResponse> joinUser(@RequestBody UserDTO userDTO) {

        CommonResponse response = userService.join(userDTO);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponse> findUserById(@PathVariable String userId) {

        CommonResponse response = userService.findUserById(userId);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
