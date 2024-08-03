package likelion.senifood.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserDietLikeId.class)
public class UserDietLike {

    @Id
    private String userId;
    @Id
    private Integer dietId;
}
