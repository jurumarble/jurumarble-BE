package co.kr.jurumarble.drink.service;

import co.kr.jurumarble.drink.controller.request.DrinkData;
import co.kr.jurumarble.drink.controller.response.GetHotDrinksResponse;
import co.kr.jurumarble.drink.controller.response.GetMapInDrinksResponse;
import co.kr.jurumarble.drink.domain.EnjoyDrinkValidator;
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
    private final EnjoyDrinkValidator enjoyDrinkValidator;
    private final PageableConverter pageableConverter;
    private final UserRepository userRepository;


    public GetDrinkServiceResponse getDrinkData(Long drinkId) {
        Drink drink = drinkRepository.findById(drinkId).orElseThrow(DrinkNotFoundException::new);
        return new GetDrinkServiceResponse(drink);
    }

    public void enjoyDrink(Long userId, EnjoyDrinkServiceRequest request) {
        enjoyDrinkValidator.checkIsAlreadyEnjoyed(userId, request.getDrinkId());
        EnjoyDrink enjoyDrink = new EnjoyDrink(userId, request.getDrinkId());
        enjoyDrinkRepository.save(enjoyDrink);
    }

    public List<GetHotDrinksResponse> getHotDrinks() {
        List<HotDrinkData> hotDrinkData = drinkRepository.getHotDrinks(FIXED_INDEX_OF_GETTING_HOT_DRINKS, NUMBER_OF_HOT_DRINK, LocalDateTime.now());
        makeHotDrinkDataHasTenData(hotDrinkData);
        return getGetHotDrinksResponses(hotDrinkData);
    }

    private void makeHotDrinkDataHasTenData(List<HotDrinkData> hotDrinkData) {
        if (hotDrinkData.size() < NUMBER_OF_HOT_DRINK) {
            int shortageOfDrink = NUMBER_OF_HOT_DRINK - hotDrinkData.size();
            List<HotDrinkData> additionalDrinks = drinkRepository.findDrinksByPopular(FIXED_INDEX_OF_GETTING_HOT_DRINKS, shortageOfDrink);
            hotDrinkData.addAll(additionalDrinks);
        }
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

        if(SortByType.ByPopularity == sortBy) {
            List<DrinkData> drinks = drinkRepository.getDrinksByPopularity(keyword, region, pageNum, pageSize);
            return pageableConverter.convertListToSlice(drinks, pageNum, pageSize);
        }

        if(SortByType.ByName == sortBy) {
            List<DrinkData> drinks = drinkRepository.getDrinksByName(keyword, region, pageNum, pageSize);
            return pageableConverter.convertListToSlice(drinks, pageNum, pageSize);
        }

        throw new SortByNotFountException();
    }

    public Slice<DrinkData> getEnjoyDrinks(Long userId, int pageNum, int pageSize) {
        List<EnjoyDrink> enjoyDrinks = enjoyDrinkRepository.findByUserId(userId);
        List<Long> drinkIds = extractDrinkIds(enjoyDrinks);
        List<DrinkData> drinksByEnjoyed = findDrinksByDrinkIds(drinkIds);
        return pageableConverter.convertListToSlice(drinksByEnjoyed, pageNum, pageSize);
    }

    private List<Long> extractDrinkIds(List<EnjoyDrink> enjoyDrinks) {
        return enjoyDrinks.stream()
                .map(EnjoyDrink::getDrinkId)
                .collect(Collectors.toList());
    }

    private List<DrinkData> findDrinksByDrinkIds(List<Long> drinkIds) {
        return drinkRepository.findDrinksByIdIn(drinkIds).stream()
                .map(DrinkData::new)
                .collect(Collectors.toList());
    }

    public boolean checkEnjoyed(Long drinkId, Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        drinkRepository.findById(drinkId).orElseThrow(DrinkNotFoundException::new);
        return enjoyDrinkRepository.findByUserIdAndDrinkId(userId, drinkId).isPresent();
    }
}
