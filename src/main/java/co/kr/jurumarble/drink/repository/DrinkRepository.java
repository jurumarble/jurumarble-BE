package co.kr.jurumarble.drink.repository;

import co.kr.jurumarble.drink.domain.dto.MapInDrinkData;
import co.kr.jurumarble.drink.domain.entity.Drink;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long>, DrinkEntityRepository {
    List<Drink> findDrinksByIdIn(List<Long> drinkIds);

    @Query("SELECT new co.kr.jurumarble.drink.domain.dto.MapInDrinkData(d.id, d.name, d.region, d.latitude, d.longitude, d.image, d.manufacturer) FROM Drink d " +
            "WHERE (d.latitude BETWEEN :startX AND :endX AND d.longitude BETWEEN :startY AND :endY) " +
            "ORDER BY d.name")
    Slice<MapInDrinkData> findDrinksByCoordinate(PageRequest pageRequest, @Param("startX") Double startX, @Param("startY") Double startY, @Param("endX") Double endX, @Param("endY") Double endY);

}