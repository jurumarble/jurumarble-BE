package co.kr.jurumarble.drink.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "drink")
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private String manufacturer;

    @Column(name = "alcoholic_beverage")
    private String alcoholicBeverage;

    @Column(name = "raw_material")
    private String rawMaterial;

    private String capacity;

    @Column(name = "manufacture_address")
    private String manufactureAddress;

    private String region;

    private String price;

    private String image;

    private Double latitude;

    private Double longitude;

    @Builder
    public Drink(Long id, String name, String type, String manufacturer, String alcoholicBeverage, String rawMaterial, String capacity, String manufactureAddress, String region, String price, String image, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.manufacturer = manufacturer;
        this.alcoholicBeverage = alcoholicBeverage;
        this.rawMaterial = rawMaterial;
        this.capacity = capacity;
        this.manufactureAddress = manufactureAddress;
        this.region = region;
        this.price = price;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drink)) return false;
        Drink drink = (Drink) o;
        return Objects.equals(id, drink.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean hasSameRegion(Drink drink) {
        return region.equals(drink.getRegion());
    }
}
