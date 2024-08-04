//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package likelion.senifood.dto;

import likelion.senifood.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
//    private String userId;
    private String name;
    private String phone;
    private int age;
    private String password;
    private String confirm_password;
    private byte gender;

    public User toEntity() {
        User user = new User();
        user.setUserId(CreateRandomAccessCode());
        user.setName(this.name);
        user.setPhone(this.phone);
        user.setAge(this.age);
        user.setPassword(this.password);
        user.setGender(this.gender);
        return user;
    }

    public String CreateRandomAccessCode() {
        Random random = new Random();
        String code = "";
        List<String> list = new ArrayList<>();
        for(int i=0; i<3; i++) {
            list.add(String.valueOf(random.nextInt(10)));
        }
        for(int i=0; i<3; i++) {
            list.add(String.valueOf((char)(random.nextInt(26)+65)));
        }

        Collections.shuffle(list);
        for(String item : list) {
            code += item;
        }
        return code;
    }
}