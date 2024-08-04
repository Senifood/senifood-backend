package likelion.senifood.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_lunchbox_subscribe")
@IdClass(SubscriptionId.class)
public class Subscription {

    @Id
    private String userId;

    @Id
    private Long lunchboxId;

    private String startingDate; // 구독 시작 날짜를 String으로 저장

}
