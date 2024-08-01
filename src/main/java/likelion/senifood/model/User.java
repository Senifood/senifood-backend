package likelion.senifood.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class User {
    @Id
    private String userId;

    private String name;
    private String phone;
    private int age;
    private String password;
    private boolean gender; //0일시 남자, 1일시 여자

    @OneToMany(mappedBy = "user")
    private List<Refresh> refreshTokens;
}
