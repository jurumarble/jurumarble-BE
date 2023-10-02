package co.kr.jurumarble.drink.controller.response;

import lombok.Getter;

@Getter
public class GetMapInDrinksResponse {

    private Long drinkId;
    private String name;
    private String region;
    private Double latitude;
    private Double longitude;
    private String image;
    private String manufacturer;

    public GetMapInDrinksResponse(Long drinkId, String name, String region, Double latitude, Double longitude, String image, String manufacturer) {
        this.drinkId = drinkId;
        this.name = name;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.manufacturer = manufacturer;
    }
}
