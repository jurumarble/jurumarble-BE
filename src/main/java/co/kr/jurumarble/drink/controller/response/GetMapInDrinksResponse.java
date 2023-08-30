package co.kr.jurumarble.drink.controller.response;

import lombok.Getter;

@Getter
public class GetMapInDrinksResponse {

    private Long drinkId;
    private String name;
    private String region;
    private Double latitude;
    private Double longitude;

    public GetMapInDrinksResponse(Long drinkId, String name, String region, Double latitude, Double longitude) {
        this.drinkId = drinkId;
        this.name = name;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
