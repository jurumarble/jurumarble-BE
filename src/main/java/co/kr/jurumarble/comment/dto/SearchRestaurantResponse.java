package co.kr.jurumarble.comment.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchRestaurantResponse {

    private String contentId;

    private String restaurantName;

    private String restaurantImage;

    private String treatMenu;

    public SearchRestaurantResponse(String contentId, String restaurantName, String restaurantImage, String treatMenu) {
        this.contentId = contentId;
        this.restaurantName = restaurantName;
        this.restaurantImage = restaurantImage;
        this.treatMenu = treatMenu;
    }
}
