package co.kr.jurumarble.drink.controller.response;

import co.kr.jurumarble.drink.controller.request.DrinkData;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
public class UsersEnjoyDrinks {
    private Long enjoyedDrinkCount;
    private Slice<DrinkData> drinkData;

    public UsersEnjoyDrinks(Long enjoyedDrinkCount, Slice<DrinkData> drinkData) {
        this.enjoyedDrinkCount = enjoyedDrinkCount;
        this.drinkData = drinkData;
    }
}
