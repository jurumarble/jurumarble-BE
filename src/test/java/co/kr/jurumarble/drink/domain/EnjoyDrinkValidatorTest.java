package co.kr.jurumarble.drink.domain;

import co.kr.jurumarble.drink.domain.entity.EnjoyDrink;
import co.kr.jurumarble.drink.repository.EnjoyDrinkRepository;
import co.kr.jurumarble.exception.drink.AlreadyEnjoyedDrinkException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnjoyDrinkValidatorTest {

    @InjectMocks
    private EnjoyDrinkValidator enjoyDrinkValidator;

    @Mock
    private EnjoyDrinkRepository enjoyDrinkRepository;

    @DisplayName("이미 즐긴 전통주 일 때 AlreadyEnjoyedDrinkException을 throw한다.")
    @Test
    void checkIsAlreadyEnjoyed() {
        // given
        when(enjoyDrinkRepository.findByUserIdAndDrinkId(anyLong(), anyLong()))
                .thenReturn(Optional.of(new EnjoyDrink(anyLong(), anyLong())));

        // when // then
        assertThrows(AlreadyEnjoyedDrinkException.class,
                () -> enjoyDrinkValidator.checkIsAlreadyEnjoyed(1L, 1L));
    }

    @DisplayName("아직 즐기지 않은 전통주의 경우 에러를 터트리지 않는다.")
    @Test
    void checkIsAlreadyEnjoyedWithNotThrowException() {
        // given
        when(enjoyDrinkRepository.findByUserIdAndDrinkId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        // when // then
        assertDoesNotThrow(() -> enjoyDrinkValidator.checkIsAlreadyEnjoyed(1L, 1L));
    }

}