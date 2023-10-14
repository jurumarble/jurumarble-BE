package co.kr.jurumarble.drink.controller.request;

import lombok.Getter;

@Getter
public class UpdateDrink {
    private Long drinkId;
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


}
