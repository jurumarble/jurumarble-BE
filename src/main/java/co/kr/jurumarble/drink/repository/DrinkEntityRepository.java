package co.kr.jurumarble.drink.repository;

import co.kr.jurumarble.drink.repository.dto.HotDrinkData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DrinkEntityRepository {

    List<HotDrinkData> getHotDrinks(Pageable pageable, LocalDateTime nowTime);
}
