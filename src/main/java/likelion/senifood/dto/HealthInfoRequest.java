package likelion.senifood.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HealthInfoRequest {
    private List<String> diseases;
    private List<String> allergies;
}
