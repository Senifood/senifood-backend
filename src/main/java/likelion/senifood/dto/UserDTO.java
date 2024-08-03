package likelion.senifood.dto;

import jakarta.persistence.OneToMany;
import likelion.senifood.entity.User;
import lombok.Data;

@Data
public class UserDTO {

    private String userId;

    private String name;

    private String phone;

    private int age;

    private String password;

    private byte gender; //0일시 남자, 1일시 여자

    public User toEntity() {
        User user = new User();

        user.setUserId(this.userId);
        user.setName(this.name);
        user.setPhone(this.phone);
        user.setAge(this.age);
        user.setPassword(this.password);
        user.setGender(this.gender);

        return user;
    }

}
