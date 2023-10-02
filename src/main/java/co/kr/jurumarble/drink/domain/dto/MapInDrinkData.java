package co.kr.jurumarble.drink.domain.dto;


import co.kr.jurumarble.drink.controller.response.GetMapInDrinksResponse;

public class MapInDrinkData {
    private Long drinkId;
    private String name;
    private String region;
    private Double latitude;
    private Double longitude;
    private String image;

    public MapInDrinkData(Long drinkId, String name, String region, Double latitude, Double longitude, String image) {
        this.drinkId = drinkId;
        this.name = name;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
    }

    public GetMapInDrinksResponse toMapInDrinksResponse() {
        return new GetMapInDrinksResponse(drinkId, name, region, latitude, longitude, image);
    }
}
