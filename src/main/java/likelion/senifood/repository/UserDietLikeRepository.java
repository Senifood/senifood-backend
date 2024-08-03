package likelion.senifood.repository;

import likelion.senifood.entity.UserDietLike;
import likelion.senifood.entity.UserDietLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDietLikeRepository extends JpaRepository<UserDietLike, UserDietLikeId> {
}
