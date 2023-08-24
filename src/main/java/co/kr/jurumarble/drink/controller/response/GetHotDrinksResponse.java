package co.kr.jurumarble.drink.controller.response;

import lombok.Getter;

@Getter
public class GetHotDrinksResponse {

    private final Long drinkId;
    private final String name;
    private final String manufactureAddress;
    private final String image;

    public GetHotDrinksResponse(Long drinkId, String name, String manufactureAddress, String image) {
        this.drinkId = drinkId;
        this.name = name;
        this.manufactureAddress = manufactureAddress;
        this.image = image;
    }
}
