package co.kr.jurumarble.drink.controller.request;

import lombok.*;

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
}
