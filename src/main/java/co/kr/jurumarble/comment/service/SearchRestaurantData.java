package co.kr.jurumarble.comment.service;

import lombok.Getter;

@Getter
public class SearchRestaurantData {

    private String contentId;

    private String restaurantName;

    private String restaurantImage;

    private String treatMenu;

    public SearchRestaurantData(String contentId, String restaurantName, String restaurantImage, String treatMenu) {
        this.contentId = contentId;
        this.restaurantName = restaurantName;
        this.restaurantImage = restaurantImage;
        this.treatMenu = treatMenu;
    }
}
