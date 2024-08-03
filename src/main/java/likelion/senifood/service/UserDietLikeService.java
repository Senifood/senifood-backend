package likelion.senifood.service;

import likelion.senifood.entity.UserDietLike;
import likelion.senifood.entity.UserDietLikeId;
import likelion.senifood.repository.UserDietLikeRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDietLikeService {

    private final UserDietLikeRepository userDietLikeRepository;

    public UserDietLikeService(UserDietLikeRepository userDietLikeRepository) {
        this.userDietLikeRepository = userDietLikeRepository;
    }

    public void saveUserDietLike(UserDietLike userDietLike) {
        userDietLikeRepository.save(userDietLike);
    }

    public void deleteUserDietLike(String userId, Integer dietId) {
        UserDietLikeId id = new UserDietLikeId();
        id.setUserId(userId);
        id.setDietId(dietId);
        userDietLikeRepository.deleteById(id);
    }
}
