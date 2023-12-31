package co.kr.jurumarble.drink.repository;

import co.kr.jurumarble.drink.controller.request.DrinkData;
import co.kr.jurumarble.drink.repository.dto.HotDrinkData;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DrinkEntityRepository {

    List<HotDrinkData> getHotDrinks(int pageNo, int pageSize, LocalDateTime nowTime);

    List<HotDrinkData> findDrinksByPopular(int pageNo, int pageSize);

    List<DrinkData> getDrinksByName(String keyword, String region, int pageNo, int pageSize);

    List<DrinkData> getDrinksByPopularity(String keyword, String region, int pageNum, int pageSize);
}
