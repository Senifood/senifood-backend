package likelion.senifood.service;

import jakarta.transaction.Transactional;
import likelion.senifood.common.CommonResponse;
import likelion.senifood.dto.UserDTO;
import likelion.senifood.model.User;
import likelion.senifood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    /*
    * 회원가입
    * */
    @Transactional
    public CommonResponse join(UserDTO userDTO) {

        User entity = userDTO.toEntity();
        userRepository.save(entity);

        return new CommonResponse(true, HttpStatus.OK, "성공", userDTO);

    }

}
