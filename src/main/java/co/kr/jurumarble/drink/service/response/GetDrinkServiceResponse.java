package co.kr.jurumarble.drink.service.response;

import co.kr.jurumarble.drink.controller.response.GetDrinkResponse;
import co.kr.jurumarble.drink.domain.entity.Drink;
import lombok.Getter;

@Getter
public class GetDrinkServiceResponse {
    private final String name;
    private final String type;
    private final String productName;
    private final String alcoholicBeverage;
    private final String rawMaterial;
    private final int capacity;
    private final String manufactureAddress;
    private final String image;

    public GetDrinkServiceResponse(Drink drink) {
        this.name = drink.getName();
        this.type = drink.getType();
        this.productName = drink.getProductName();
        this.alcoholicBeverage = drink.getAlcoholicBeverage();
        this.rawMaterial = drink.getRawMaterial();
        this.capacity = drink.getCapacity();
        this.manufactureAddress = drink.getManufactureAddress();
        this.image = drink.getImage();
    }

    public GetDrinkResponse toControllerResponse() {
        return GetDrinkResponse.builder()
                .name(name)
                .type(type)
                .productName(productName)
                .alcoholicBeverage(alcoholicBeverage)
                .rawMaterial(rawMaterial)
                .capacity(capacity)
                .manufactureAddress(manufactureAddress)
                .image(image)
                .build();
    }
}
