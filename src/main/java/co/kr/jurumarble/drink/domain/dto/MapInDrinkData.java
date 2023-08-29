package co.kr.jurumarble.drink.domain.dto;


import co.kr.jurumarble.drink.controller.response.GetMapInDrinksResponse;

public class MapInDrinkData {
    private Long drinkId;
    private String productName;
    private String region;
    private Double latitude;
    private Double longitude;

    public MapInDrinkData(Long drinkId, String productName, String region, Double latitude, Double longitude) {
        this.drinkId = drinkId;
        this.productName = productName;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GetMapInDrinksResponse toMapInDrinksResponse() {
        return new GetMapInDrinksResponse(drinkId, productName, region, latitude, longitude);
    }
}
