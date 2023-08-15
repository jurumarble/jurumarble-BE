package co.kr.jurumarble.drink.service.request;

import lombok.Getter;

@Getter
public class EnjoyDrinkServiceRequest {

    private final Long drinkId;

    public EnjoyDrinkServiceRequest(Long drinkId) {
        this.drinkId = drinkId;
    }
}