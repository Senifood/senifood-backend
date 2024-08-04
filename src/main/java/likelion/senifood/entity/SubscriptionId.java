package likelion.senifood.entity;

import jakarta.persistence.Table;
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
public class SubscriptionId implements Serializable {

    private String userId;
    private Long lunchboxId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionId that = (SubscriptionId) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(lunchboxId, that.lunchboxId);
    }

    @Override
    public int hashCode() { return Objects.hash(userId, lunchboxId); }
}
