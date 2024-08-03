package likelion.senifood.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDietLikeId implements Serializable {
    @Getter

    private String userId;
    private Integer dietId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDietLikeId that = (UserDietLikeId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(dietId, that.dietId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, dietId);
    }
}
