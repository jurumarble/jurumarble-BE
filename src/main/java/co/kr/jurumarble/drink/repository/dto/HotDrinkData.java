package co.kr.jurumarble.drink.repository.dto;

import co.kr.jurumarble.drink.controller.response.GetHotDrinksResponse;
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

    public GetHotDrinksResponse toHotDrinksResponse() {
        return new GetHotDrinksResponse(drinkId, name, manufactureAddress, image);
    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        HotDrinkData that = (HotDrinkData) o;
//        return Objects.equals(drinkId, that.drinkId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(drinkId);
//    }
}
