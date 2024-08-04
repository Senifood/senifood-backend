package likelion.senifood.repository;

import likelion.senifood.entity.SubscriptionId;
import likelion.senifood.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {
    List<Subscription> findByUserId(String userId);
}