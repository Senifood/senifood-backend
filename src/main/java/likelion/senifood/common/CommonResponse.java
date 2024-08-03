package likelion.senifood.common;

import likelion.senifood.dto.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter @Setter
public class CommonResponse {
    private boolean isSuccess;
    HttpStatus status;
    private String msg;
    private Object object;

    public CommonResponse(Object object) {
        this.object = object;
    }

    public CommonResponse(HttpStatus status, String msg) {
        this.status = status;
        this.msg = msg;
    }

}
