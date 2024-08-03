package likelion.senifood.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "user_lunchbox_subscribe")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "lunchbox_id")
    private Long lunchboxId;

    @Column(name = "starting_date")
    private String startingDate; // 구독 시작 날짜를 String으로 저장

}
