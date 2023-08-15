package co.kr.jurumarble.drink.domain;

import co.kr.jurumarble.drink.repository.EnjoyDrinkRepository;
import co.kr.jurumarble.exception.drink.AlreadyEnjoyedDrinkException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnjoyDrinkValidator {

    private final EnjoyDrinkRepository enjoyDrinkRepository;

    public void checkIsAlreadyEnjoyed(Long userId, Long drinkId) {
        enjoyDrinkRepository.findByUserIdAndDrinkId(userId, drinkId)
                .ifPresent(enjoyDrink -> {
                    throw new AlreadyEnjoyedDrinkException();
                });
    }
}
