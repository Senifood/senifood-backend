package likelion.senifood.repository;

import likelion.senifood.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByPhone(String phoneNumber);
}
