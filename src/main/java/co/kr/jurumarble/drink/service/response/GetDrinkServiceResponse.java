package co.kr.jurumarble.drink.service.response;

import co.kr.jurumarble.drink.controller.response.GetDrinkResponse;
import co.kr.jurumarble.drink.domain.entity.Drink;
import lombok.Getter;

@Getter
public class GetDrinkServiceResponse {
    private final Long drinkId;
    private final String name;
    private final String type;
    private final String manufacturer;
    private final String alcoholicBeverage;
    private final String rawMaterial;
    private final String capacity;
    private final String manufactureAddress;
    private final String image;

    public GetDrinkServiceResponse(Drink drink) {
        this.drinkId = drink.getId();
        this.name = drink.getName();
        this.type = drink.getType();
        this.manufacturer = drink.getManufacturer();
        this.alcoholicBeverage = drink.getAlcoholicBeverage();
        this.rawMaterial = drink.getRawMaterial();
        this.capacity = drink.getCapacity();
        this.manufactureAddress = drink.getManufactureAddress();
        this.image = drink.getImage();
    }

    public GetDrinkResponse toControllerResponse() {
        return GetDrinkResponse.builder()
                .drinkId(drinkId)
                .name(name)
                .type(type)
                .manufacturer(manufacturer)
                .alcoholicBeverage(alcoholicBeverage)
                .rawMaterial(rawMaterial)
                .capacity(capacity)
                .manufactureAddress(manufactureAddress)
                .image(image)
                .build();
    }
}
