package co.kr.jurumarble.drink.controller.request;

import co.kr.jurumarble.drink.domain.entity.Drink;
import lombok.Getter;

@Getter
public class AddDrink {
    private String name;
    private String type;
    private String manufacturer;
    private String alcoholicBeverage;
    private String rawMaterial;
    private String capacity;
    private String manufactureAddress;
    private String region;
    private String price;
    private String image;
    private Double latitude;
    private Double longitude;

    public Drink toEntity() {
        return Drink.builder()
                .name(name)
                .type(type)
                .manufacturer(manufacturer)
                .alcoholicBeverage(alcoholicBeverage)
                .rawMaterial(rawMaterial)
                .capacity(capacity)
                .manufactureAddress(manufactureAddress)
                .region(region)
                .price(price)
                .image(image)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
