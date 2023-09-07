package co.kr.jurumarble.drink.controller.request;

import co.kr.jurumarble.drink.domain.entity.Drink;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrinkData {

    private Long id;
    private String name;
    private String type;
    private String productName;
    private String alcoholicBeverage;
    private String rawMaterial;
    private String capacity;
    private String manufactureAddress;
    private String region;
    private String price;
    private String image;
    private Double latitude;
    private Double longitude;

    public DrinkData(Drink drink) {
        this.id = drink.getId();
        this.name = drink.getName();
        this.type = drink.getType();
        this.productName = drink.getProductName();
        this.alcoholicBeverage = drink.getAlcoholicBeverage();
        this.rawMaterial = drink.getRawMaterial();
        this.capacity = drink.getCapacity();
        this.manufactureAddress = drink.getManufactureAddress();
        this.region = drink.getRegion();
        this.price = drink.getPrice();
        this.image = drink.getImage();
        this.latitude = drink.getLatitude();
        this.longitude = drink.getLongitude();
    }
}
