package co.kr.jurumarble.drink.repository;

import co.kr.jurumarble.drink.controller.request.DrinkData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnjoyDrinkEntityRepository {
    List<DrinkData> findDrinksByUserId(Long userId, String regionName, int pageNum, int pageSize);
}
