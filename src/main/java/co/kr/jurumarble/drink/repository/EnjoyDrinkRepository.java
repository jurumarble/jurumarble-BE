package co.kr.jurumarble.drink.repository;

import co.kr.jurumarble.drink.domain.entity.EnjoyDrink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnjoyDrinkRepository extends JpaRepository<EnjoyDrink, Long>, EnjoyDrinkEntityRepository{

    Optional<EnjoyDrink> findByUserIdAndDrinkId(Long userId, Long drinkId);

    List<EnjoyDrink> findByUserId(Long userId);

    long countByDrinkId(Long drinkId);

}
