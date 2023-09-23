package co.kr.jurumarble.drink.repository;

import co.kr.jurumarble.drink.controller.request.DrinkData;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static co.kr.jurumarble.drink.domain.entity.QDrink.drink;
import static co.kr.jurumarble.drink.domain.entity.QEnjoyDrink.enjoyDrink;

@Repository
public class EnjoyDrinkEntityRepositoryImpl implements EnjoyDrinkEntityRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public EnjoyDrinkEntityRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<DrinkData> findDrinksByUserId(Long userId, String region, int pageNum, int pageSize) {

        BooleanBuilder searchConditions = new BooleanBuilder();
        setSearchConditions(userId, region, searchConditions);

        return jpaQueryFactory.select(
                Projections.bean(DrinkData.class,
                        drink.id,
                        drink.name,
                        drink.type,
                        drink.manufacturer,
                        drink.alcoholicBeverage,
                        drink.rawMaterial,
                        drink.capacity,
                        drink.manufactureAddress,
                        drink.region,
                        drink.price,
                        drink.image,
                        drink.latitude,
                        drink.longitude,
                        enjoyDrink.count().as("enjoyCount")
                ))
                .from(enjoyDrink)
                .innerJoin(drink)
                .on(enjoyDrink.drinkId.eq(drink.id))
                .where(searchConditions)
                .groupBy(drink.id)
                .orderBy(enjoyDrink.createdDate.desc())
                .offset(pageNum * pageSize)
                .limit(pageSize)
                .fetch();

    }

    private void setSearchConditions(Long userId, String region, BooleanBuilder builder) {
        builder.and(enjoyDrink.userId.eq(userId));
        if (region != null && !region.isEmpty()) {
            builder.and(drink.region.eq(region));
        }
    }
}
