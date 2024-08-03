package likelion.senifood.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class CommonResponse {
    private boolean isSuccess;
    HttpStatus status;
    private String msg;
    private Object object;


    public CommonResponse(Object object) {
        this.status = HttpStatus.OK;
        this.object = object;
    }

    public CommonResponse(HttpStatus status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public CommonResponse(boolean isSuccess, HttpStatus status, String msg, Object object) {
        this.isSuccess = isSuccess;
        this.status = status;
        this.msg = msg;
        this.object = object;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public String getMsg() {
        return this.msg;
    }

    public Object getObject() {
        return this.object;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
