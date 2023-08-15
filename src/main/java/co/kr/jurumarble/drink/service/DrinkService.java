package co.kr.jurumarble.drink.service;

import co.kr.jurumarble.drink.domain.entity.Drink;
import co.kr.jurumarble.drink.domain.entity.EnjoyDrink;
import co.kr.jurumarble.drink.repository.DrinkRepository;
import co.kr.jurumarble.drink.repository.EnjoyDrinkRepository;
import co.kr.jurumarble.drink.service.request.EnjoyDrinkServiceRequest;
import co.kr.jurumarble.drink.service.response.GetDrinkServiceResponse;
import co.kr.jurumarble.exception.drink.DrinkNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DrinkService {

    private final DrinkRepository drinkRepository;
    private final EnjoyDrinkRepository enjoyDrinkRepository;

    public GetDrinkServiceResponse getDrinkData(Long drinkId) {
        Drink drink = drinkRepository.findById(drinkId).orElseThrow(DrinkNotFoundException::new);
        return new GetDrinkServiceResponse(drink);
    }

    public void enjoyDrink(Long userId, EnjoyDrinkServiceRequest request) {
        EnjoyDrink enjoyDrink = new EnjoyDrink(userId, request.getDrinkId());
        enjoyDrinkRepository.save(enjoyDrink);
    }
}
