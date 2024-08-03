package likelion.senifood.service;

import jakarta.transaction.Transactional;
import likelion.senifood.common.CommonResponse;
import likelion.senifood.dto.UserDTO;
import likelion.senifood.entity.User;
import likelion.senifood.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    /*
      * 회원가입
    * */
    @Transactional
    public CommonResponse join(UserDTO userDTO) {

        User entity = userDTO.toEntity();
        userRepository.save(entity);

        return new CommonResponse(true, HttpStatus.OK, "성공", userDTO);

    }

    /*
     * 사용자 조회
     * */
    @Transactional
    public CommonResponse findUserById(String userId) {

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO();

            userDTO.setName(user.getName());
            userDTO.setPhone(user.getPhone());
            userDTO.setAge(user.getAge());
            userDTO.setGender(user.getGender());
            // 비밀번호는 제외하고 반환

            return new CommonResponse(true, HttpStatus.OK, "사용자 조회 성공", userDTO);
        } else {
            return new CommonResponse(false, HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다", null);
        }

    }
}
