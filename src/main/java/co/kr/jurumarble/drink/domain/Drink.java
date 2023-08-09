package co.kr.jurumarble.drink.domain;

import co.kr.jurumarble.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Drink extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


//    type
//
//
//    private String productName;
//
//
//    alcoholic_beverage
//    raw_meterial
//    capacity
//    manufacture_adress
//    price
//    image

}
