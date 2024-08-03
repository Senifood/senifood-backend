package likelion.senifood.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lunchbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lunchbox_id;
    private String lunchbox_title;
    private String lunchbox_imageURL;
    private String lunchbox_price;
    private boolean is_subscribed;
    private String lunchbox_foods;

    public boolean isIs_subscribed() {
        return is_subscribed;
    }
}
