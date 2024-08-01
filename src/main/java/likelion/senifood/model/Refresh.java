package likelion.senifood.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Refresh {
    @Id
    private String accessToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isRegister;
}
