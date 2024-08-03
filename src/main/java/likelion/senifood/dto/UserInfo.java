package likelion.senifood.dto;

import likelion.senifood.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserInfo {

    private String name;
    private String phone;
    private int age;
    private byte gender; //0일시 남자, 1일시 여자

    public User toEntity() {
        User user = new User();

        user.setName(this.name);
        user.setPhone(this.phone);
        user.setAge(this.age);
        user.setGender(this.gender);

        return user;
    }
}

