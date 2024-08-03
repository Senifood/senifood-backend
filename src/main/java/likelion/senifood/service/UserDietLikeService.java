package likelion.senifood.service;

import likelion.senifood.entity.Diet;
import likelion.senifood.entity.UserDietLike;
import likelion.senifood.entity.UserDietLikeId;
import likelion.senifood.repository.UserDietLikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDietLikeService {

    private final UserDietLikeRepository userDietLikeRepository;
    private final DietService dietService;

    public UserDietLikeService(UserDietLikeRepository userDietLikeRepository, DietService dietService) {
        this.userDietLikeRepository = userDietLikeRepository;
        this.dietService = dietService;
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

    public List<Diet> getUserLikedDiets(String userId) {
        List<UserDietLike> userLikes = userDietLikeRepository.findByUserId(userId);
        return userLikes.stream()
                .map(like -> dietService.findById(like.getDietId()).orElse(null))
                .filter(diet -> diet != null)
                .collect(Collectors.toList());
    }
}
