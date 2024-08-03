package likelion.senifood.controller;

import likelion.senifood.common.CommonResponse;
import likelion.senifood.dto.UserDTO;
import likelion.senifood.entity.User;
import likelion.senifood.entity.UserSurveyResponse;
import likelion.senifood.service.UserService;
import likelion.senifood.service.UserSurveyResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserSurveyResponseService userSurveyResponseService;

    public UserController(UserService userService, UserSurveyResponseService userSurveyResponseService) {
        this.userService = userService;
        this.userSurveyResponseService = userSurveyResponseService;
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
    public ResponseEntity<CommonResponse> findUserById(@PathVariable("userId") String userId) {

        CommonResponse response = userService.findUserById(userId);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/health/{user_id}")
    public ResponseEntity<Map<String, List<String>>> getUserResponses(@PathVariable("user_id") String userId) {
        UserSurveyResponse response = userSurveyResponseService.getUserResponses(userId).get(0);

        // ,로 구분된 문자열을 리스트로 변환
        List<String> answer1List = Arrays.asList(response.getAnswer_1().split(","));
        List<String> answer2List = Arrays.asList(response.getAnswer_2().split(","));
        List<String> answer3List = Arrays.asList(response.getAnswer_3().split(","));

        Map<String, List<String>> responses = new HashMap<>();
        responses.put("diseases", answer1List);
        responses.put("allergies", answer2List);
        responses.put("drug", answer3List);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
