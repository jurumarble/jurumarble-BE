package co.kr.jurumarble.drink.domain;

import co.kr.jurumarble.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Drink{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "alcoholic_beverage")
    private String alcoholicBeverage;

    @Column(name = "raw_material")
    private String rawMaterial;

    private int capacity;

    @Column(name = "manufacture_address")
    private String manufactureAddress;

    private int price;

    private String image;

    @Builder
    public Drink(Long id,String name, String type, String productName, String alcoholicBeverage, String rawMaterial, int capacity, String manufactureAddress, int price, String image) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.productName = productName;
        this.alcoholicBeverage = alcoholicBeverage;
        this.rawMaterial = rawMaterial;
        this.capacity = capacity;
        this.manufactureAddress = manufactureAddress;
        this.price = price;
        this.image = image;
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
}
