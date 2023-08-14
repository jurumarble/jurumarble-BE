package co.kr.jurumarble.comment.domain;

import co.kr.jurumarble.comment.dto.request.UpdateSnackRequest;
import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {
    private String restaurantName;

    private String restaurantImage;

    private String treatMenu;

    public void updateRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public void updateTreatMenu(String treatMenu) {
        this.treatMenu = treatMenu;
    }

    public void updateRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}

