package likelion.senifood.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LunchboxDTO {

    private Long lunchbox_id;
    private String lunchbox_title;
    private String lunchbox_imageURL;
    private String lunchbox_price;
    @JsonProperty("is_subscribed")
    private boolean is_subscribed;
}
