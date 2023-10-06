package co.kr.jurumarble.drink.repository;

import co.kr.jurumarble.drink.controller.request.DrinkData;
import co.kr.jurumarble.drink.repository.dto.HotDrinkData;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static co.kr.jurumarble.drink.domain.entity.QDrink.drink;
import static co.kr.jurumarble.drink.domain.entity.QEnjoyDrink.enjoyDrink;

@Repository
public class DrinkEntityRepositoryImpl implements DrinkEntityRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public DrinkEntityRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * 지금 시간 기준으로 부터 7일간 즐겼어요가 많은 순으로 전통주 조회
     */
    @Override
    public List<HotDrinkData> getHotDrinks(int pageNo, int pageSize, LocalDateTime nowTime) {

        LocalDateTime descendingHourTime = nowTime.withMinute(0);
        LocalDateTime aWeekAgoTime = descendingHourTime.minus(7, ChronoUnit.DAYS);

        return jpaQueryFactory.select(
                        Projections.bean(HotDrinkData.class,
                                drink.id.as("drinkId"),
                                drink.name,
                                drink.region.as("manufactureAddress"),
                                drink.image,
                                drink.id.count().as("enjoyedCount")
                        ))
                .from(drink)
                .leftJoin(enjoyDrink)
                .on(drink.id.eq(enjoyDrink.drinkId))
                .groupBy(drink.id)
                .where(enjoyDrink.createdDate.goe(aWeekAgoTime).and(enjoyDrink.createdDate.loe(descendingHourTime)))
                .orderBy(drink.id.count().desc(), drink.name.asc())
                .offset(pageNo * pageSize)
                .limit(pageSize)
                .fetch();
    }

    @Override
    public List<HotDrinkData> findDrinksByPopular(int pageNo, int pageSize) {
        return jpaQueryFactory.select(
                        Projections.bean(HotDrinkData.class,
                                drink.id.as("drinkId"),
                                drink.name,
                                drink.region.as("manufactureAddress"),
                                drink.image,
                                drink.id.count().as("enjoyedCount")
                        ))
                .from(drink)
                .leftJoin(enjoyDrink)
                .on(drink.id.eq(enjoyDrink.drinkId))
                .groupBy(drink.id)
                .orderBy(drink.id.count().desc(), drink.name.asc())
                .offset(pageNo * pageSize)
                .limit(pageSize)
                .fetch();
    }

    @Override
    public List<DrinkData> getDrinksByName(String keyword, String region, int pageNo, int pageSize) {

        BooleanBuilder searchConditions = new BooleanBuilder();
        setSearchConditions(keyword, region, searchConditions);

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
                .from(drink)
                .leftJoin(enjoyDrink)
                .on(drink.id.eq(enjoyDrink.drinkId))
                .where(searchConditions)
                .groupBy(drink.id)
                .orderBy(drink.name.asc())
                .offset(pageNo * pageSize)
                .limit(pageSize + 1)
                .fetch();
    }

    @Override
    public List<DrinkData> getDrinksByPopularity(String keyword, String region, int pageNo, int pageSize) {

        BooleanBuilder searchConditions = new BooleanBuilder();
        setSearchConditions(keyword, region, searchConditions);

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
                .from(drink)
                .leftJoin(enjoyDrink)
                .on(drink.id.eq(enjoyDrink.drinkId))
                .where(searchConditions)
                .groupBy(drink.id)
                .orderBy(drink.id.count().desc(), drink.name.asc())
                .offset(pageNo * pageSize)
                .limit(pageSize + 1)
                .fetch();

    }

    private void setSearchConditions(String keyword, String region, BooleanBuilder builder) {
        if (keyword != null && !keyword.isEmpty()) {
            builder.and(drink.name.like("%" + keyword + "%"));
        }

        if (region != null && !region.isEmpty()) {
            builder.and(drink.region.eq(region));
        }
    }

}

