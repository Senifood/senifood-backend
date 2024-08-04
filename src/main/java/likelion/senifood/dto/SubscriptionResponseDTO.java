package likelion.senifood.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponseDTO {
    private String startingDate;
    private Long lunchboxId;
    private String lunchboxTitle;
    private String lunchboxImageURL;
    private String lunchboxPrice;
    private String expirationDate;
}
