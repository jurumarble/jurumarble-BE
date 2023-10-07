package co.kr.jurumarble.drink.repository.dto;

import co.kr.jurumarble.drink.controller.response.GetHotDrinksResponse;
import lombok.*;

import java.util.Objects;

@Getter
@Setter // QueryDsl 때문에 필요함
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "drinkId")
public class HotDrinkData {

    private Long drinkId;
    private String name;
    private String manufactureAddress;
    private String image;
    private Long enjoyedCount;

    public GetHotDrinksResponse toHotDrinksResponse() {
        return new GetHotDrinksResponse(drinkId, name, manufactureAddress, image);
    }
}
