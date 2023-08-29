package co.kr.jurumarble.drink.controller.response;

import lombok.Getter;

@Getter
public class GetMapInDrinksResponse {

    private Long drinkId;
    private String productName;
    private String region;
    private Double latitude;
    private Double longitude;

    public GetMapInDrinksResponse(Long drinkId, String productName, String region, Double latitude, Double longitude) {
        this.drinkId = drinkId;
        this.productName = productName;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
