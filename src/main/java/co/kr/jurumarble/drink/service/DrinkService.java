package co.kr.jurumarble.drink.service;

import co.kr.jurumarble.drink.controller.request.AddDrink;
import co.kr.jurumarble.drink.controller.request.AddImage;
import co.kr.jurumarble.drink.controller.request.DrinkData;
import co.kr.jurumarble.drink.controller.request.UpdateDrink;
import co.kr.jurumarble.drink.controller.response.GetHotDrinksResponse;
import co.kr.jurumarble.drink.controller.response.GetMapInDrinksResponse;
import co.kr.jurumarble.drink.domain.dto.MapInDrinkData;
import co.kr.jurumarble.drink.domain.entity.Drink;
import co.kr.jurumarble.drink.domain.entity.EnjoyDrink;
import co.kr.jurumarble.drink.repository.DrinkRepository;
import co.kr.jurumarble.drink.repository.EnjoyDrinkRepository;
import co.kr.jurumarble.drink.repository.dto.HotDrinkData;
import co.kr.jurumarble.drink.service.request.EnjoyDrinkServiceRequest;
import co.kr.jurumarble.drink.service.response.GetDrinkServiceResponse;
import co.kr.jurumarble.exception.drink.DrinkNotFoundException;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.exception.vote.SortByNotFountException;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.utils.PageableConverter;
import co.kr.jurumarble.vote.enums.SortByType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DrinkService {

    private static final int FIXED_INDEX_OF_GETTING_HOT_DRINKS = 0;
    private static final int NUMBER_OF_HOT_DRINK = 10;

    private final DrinkRepository drinkRepository;
    private final EnjoyDrinkRepository enjoyDrinkRepository;
    private final PageableConverter pageableConverter;
    private final UserRepository userRepository;


    public GetDrinkServiceResponse getDrinkData(Long drinkId) {
        Drink drink = drinkRepository.findById(drinkId).orElseThrow(DrinkNotFoundException::new);
        long enjoyCount = enjoyDrinkRepository.countByDrinkId(drink.getId());
        return new GetDrinkServiceResponse(drink,enjoyCount);
    }

    @Transactional
    public void enjoyDrink(Long userId, EnjoyDrinkServiceRequest request) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        drinkRepository.findById(request.getDrinkId()).orElseThrow(DrinkNotFoundException::new);
        enjoyDrinkRepository.findByUserIdAndDrinkId(userId, request.getDrinkId()).ifPresentOrElse(
                enjoyDrinkRepository::delete,
                () -> {
                    EnjoyDrink enjoyDrink = new EnjoyDrink(userId, request.getDrinkId());
                    enjoyDrinkRepository.save(enjoyDrink);
                }
        );
    }

    public List<GetHotDrinksResponse> getHotDrinks() {
        List<HotDrinkData> hotDrinkData = drinkRepository.getHotDrinks(FIXED_INDEX_OF_GETTING_HOT_DRINKS, NUMBER_OF_HOT_DRINK, LocalDateTime.now());
        makeHotDrinkDataHasTenData(hotDrinkData);
        return getGetHotDrinksResponses(hotDrinkData);
    }

    private void makeHotDrinkDataHasTenData(List<HotDrinkData> hotDrinkDatas) {
        if (hotDrinkDatas.size() < NUMBER_OF_HOT_DRINK) {
            List<HotDrinkData> additionalDrinks = drinkRepository.findDrinksByPopular(FIXED_INDEX_OF_GETTING_HOT_DRINKS, NUMBER_OF_HOT_DRINK);
            additionalDrinks
                    .forEach(additionalHotDrink -> {
                                if (conditionToAddNewDrink(hotDrinkDatas, additionalHotDrink)) {
                                    hotDrinkDatas.add(additionalHotDrink);
                                }
                            }
                    );
        }
    }

    private boolean conditionToAddNewDrink(List<HotDrinkData> hotDrinkDatas, HotDrinkData additionalHotDrink) {
        return !hotDrinkDatas.contains(additionalHotDrink) && hotDrinkDatas.size() < NUMBER_OF_HOT_DRINK;
    }

    private List<GetHotDrinksResponse> getGetHotDrinksResponses(List<HotDrinkData> hotDrinks) {
        return hotDrinks.stream()
                .map(HotDrinkData::toHotDrinksResponse)
                .collect(Collectors.toList());
    }

    public Slice<GetMapInDrinksResponse> getMapInDrinks(Double startX, Double startY, Double endX, Double endY, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Slice<MapInDrinkData> drinkData = drinkRepository.findDrinksByCoordinate(pageRequest, startX, startY, endX, endY);
        return new SliceImpl<>(getGetMapInDrinksResponses(drinkData), drinkData.getPageable(), drinkData.hasNext());

    }

    private List<GetMapInDrinksResponse> getGetMapInDrinksResponses(Slice<MapInDrinkData> drinkData) {
        return drinkData.stream()
                .map(MapInDrinkData::toMapInDrinksResponse)
                .collect(Collectors.toList());
    }

    public Slice<DrinkData> getDrinks(String keyword, String region, SortByType sortBy, int pageNum, int pageSize) {

        if (SortByType.ByPopularity == sortBy) {
            List<DrinkData> drinks = drinkRepository.getDrinksByPopularity(keyword, region, pageNum, pageSize);
            return pageableConverter.convertListToSlice(drinks, pageNum, pageSize);
        }

        if (SortByType.ByName == sortBy) {
            List<DrinkData> drinks = drinkRepository.getDrinksByName(keyword, region, pageNum, pageSize);
            return pageableConverter.convertListToSlice(drinks, pageNum, pageSize);
        }

        throw new SortByNotFountException();
    }

    public Slice<DrinkData> getEnjoyDrinks(Long userId, String regionName, int pageNum, int pageSize) {
        List<DrinkData> drinksByEnjoyed = enjoyDrinkRepository.findDrinksByUserId(userId, regionName, pageNum, pageSize);
        return pageableConverter.convertListToSlice(drinksByEnjoyed, pageNum, pageSize);
    }

    public boolean checkEnjoyed(Long drinkId, Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        drinkRepository.findById(drinkId).orElseThrow(DrinkNotFoundException::new);
        return enjoyDrinkRepository.findByUserIdAndDrinkId(userId, drinkId).isPresent();
    }

    @Transactional
    public void addDrinkImage(AddImage image) {
        Drink drink = drinkRepository.findById(image.getDrinkId()).orElseThrow(RuntimeException::new);
        drink.updateImage(image.getImageUrl());
    }

    @Transactional
    public Long addDrink(AddDrink drinkInfo) {
        Drink drink = drinkInfo.toEntity();
        return drinkRepository.save(drink).getId();
    }

    @Transactional
    public Long updateDrink(UpdateDrink drinkInfo) {
        Drink drink = drinkRepository.findById(drinkInfo.getDrinkId()).orElseThrow(RuntimeException::new);
        drink.updateDrink(drinkInfo);
        return drink.getId();
    }
}
