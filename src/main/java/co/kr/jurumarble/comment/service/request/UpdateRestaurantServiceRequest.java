package co.kr.jurumarble.comment.service.request;

import lombok.*;

@Getter
public class UpdateRestaurantServiceRequest {
    private String restaurantName;
    private String restaurantImage;

    @Builder
    public UpdateRestaurantServiceRequest(String restaurantName, String restaurantImage) {
        this.restaurantName = restaurantName;
        this.restaurantImage = restaurantImage;
    }
}
