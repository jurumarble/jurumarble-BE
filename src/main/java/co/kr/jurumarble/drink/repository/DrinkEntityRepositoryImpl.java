package co.kr.jurumarble.drink.repository;

import co.kr.jurumarble.drink.repository.dto.HotDrinkData;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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
    public Slice<HotDrinkData> getHotDrinks(Pageable pageable, LocalDateTime nowTime) {
        int pageNo = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        LocalDateTime descendingHourTime = nowTime.withMinute(0);
        LocalDateTime aWeekAgoTime = descendingHourTime.minus(7, ChronoUnit.DAYS);

        List<HotDrinkData> hotDrinkDataList = jpaQueryFactory.select(
                        Projections.bean(HotDrinkData.class,
                                drink.id,
                                drink.name,
                                drink.manufactureAddress,
                                drink.image,
                                drink.id.count()
                        ))
                .from(drink)
                .leftJoin(enjoyDrink)
                .on(drink.id.eq(enjoyDrink.drinkId))
                .groupBy(drink.id)
                .where(enjoyDrink.createdDate.goe(aWeekAgoTime).and(enjoyDrink.createdDate.loe(descendingHourTime)))
                .orderBy(drink.id.count().desc())
                .offset(pageNo * pageSize)
                .limit(pageSize)
                .fetch();

        boolean hasNext = false;
        if (hotDrinkDataList.size() > pageSize) {
            hasNext = true;
            hotDrinkDataList = hotDrinkDataList.subList(0, pageSize); // 조회된 결과에서 실제 페이지의 데이터만 가져옴
        }

        return new SliceImpl<>(hotDrinkDataList, pageable, hasNext);
    }
}
