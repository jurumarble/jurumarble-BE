package co.kr.jurumarble.drink.service;

import co.kr.jurumarble.drink.domain.Drink;
import co.kr.jurumarble.drink.repository.DrinkRepository;
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

    public GetDrinkServiceResponse getDrinkData(Long drinkId) {
        Drink drink = drinkRepository.findById(drinkId).orElseThrow(DrinkNotFoundException::new);
        return new GetDrinkServiceResponse(drink);
    }
}
