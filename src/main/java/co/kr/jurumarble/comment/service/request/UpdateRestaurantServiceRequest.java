package co.kr.jurumarble.comment.service.request;

import lombok.*;

@Getter
public class UpdateRestaurantServiceRequest {
    private String restaurantName;
    private String restaurantImage;
    private String treatMenu;

    @Builder
    public UpdateRestaurantServiceRequest(String restaurantName, String restaurantImage, String treatMenu) {
        this.restaurantName = restaurantName;
        this.restaurantImage = restaurantImage;
        this.treatMenu = treatMenu;
    }
}
