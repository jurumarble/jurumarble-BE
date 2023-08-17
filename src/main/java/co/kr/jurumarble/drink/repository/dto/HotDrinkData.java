package co.kr.jurumarble.drink.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // QueryDsl 때문에 필요함
@NoArgsConstructor
@AllArgsConstructor
public class HotDrinkData {

    private Long drinkId;
    private String name;
    private String manufactureAddress;
    private String image;
    private Long enjoyedCount;
}
