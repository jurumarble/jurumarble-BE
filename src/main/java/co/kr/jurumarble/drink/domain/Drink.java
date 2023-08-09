package co.kr.jurumarble.drink.domain;

import co.kr.jurumarble.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Drink extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "alcoholic_beverage")
    private int alcoholicBeverage;

    @Column(name = "raw_material")
    private String rawMaterial;

    private int capacity;

    @Column(name = "manufacture_address")
    private String manufactureAddress;

    private int price;
    
    private String image;

}
