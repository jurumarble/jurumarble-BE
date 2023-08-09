package co.kr.jurumarble.drink.repository;

import co.kr.jurumarble.drink.domain.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {

}
