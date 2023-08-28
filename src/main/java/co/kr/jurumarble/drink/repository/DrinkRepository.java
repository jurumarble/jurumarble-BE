package co.kr.jurumarble.drink.repository;

import co.kr.jurumarble.drink.domain.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long>, DrinkEntityRepository {

    List<Drink> findDrinksByIdIn(List<Long> drinkIds);
}
