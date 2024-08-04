package likelion.senifood.dto;

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
}
