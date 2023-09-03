package co.kr.jurumarble.comment.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {
    private String restaurantName;

    private String restaurantImage;


    public void updateRestaurant(String restaurantName, String restaurantImage) {
        if (restaurantName != null) {
            this.restaurantName = restaurantName;
        }
        if (restaurantImage != null) {
            this.restaurantImage = restaurantImage;
        }
    }

}

