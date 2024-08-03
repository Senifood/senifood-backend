package likelion.senifood;

import likelion.senifood.common.CommonResponse;
import likelion.senifood.dto.UserDTO;
import likelion.senifood.entity.User;

import likelion.senifood.repository.UserRepository;
import likelion.senifood.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testJoin() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("user123");
        userDTO.setName("sam smith");
        userDTO.setPhone("010-5555-333");
        userDTO.setAge(20);
        userDTO.setPassword("password123");
        userDTO.setGender((byte) 0);

        User user = userDTO.toEntity();
        user.setPassword("encodedPassword");

        // When
        CommonResponse response = userService.join(userDTO);

        // Then
        assertEquals(true, response.isSuccess());
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("성공", response.getMsg());

        UserDTO savedUserDTO = (UserDTO) response.getObject();
        assertEquals(userDTO.getUserId(), savedUserDTO.getUserId());
        assertEquals(userDTO.getName(), savedUserDTO.getName());
        assertEquals(userDTO.getPhone(), savedUserDTO.getPhone());
        assertEquals(userDTO.getAge(), savedUserDTO.getAge());
        assertEquals(userDTO.getGender(), savedUserDTO.getGender());
    }

    @Test
    void testFindUserById() {
        // Given
        User user = new User();
        user.setUserId("user123");
        user.setName("John Doe");
        user.setPhone("123-456-7890");
        user.setAge(30);
        user.setPassword("password123");
        user.setGender((byte) 0);

        userRepository.save(user);

        // When
        CommonResponse response = userService.findUserById("user123");

        // Then
        assertEquals(true, response.isSuccess());
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("사용자 조회 성공", response.getMsg());

        UserDTO userDTO = (UserDTO) response.getObject();
        assertEquals(user.getUserId(), userDTO.getUserId());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getPhone(), userDTO.getPhone());
        assertEquals(user.getAge(), userDTO.getAge());
        assertEquals(user.getGender(), userDTO.getGender());
    }
}
