package co.kr.jurumarble.drink.service;

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

    private final DrinkRepository drinkRepository;
    private final EnjoyDrinkRepository enjoyDrinkRepository;
    private final EnjoyDrinkValidator enjoyDrinkValidator;

    public GetDrinkServiceResponse getDrinkData(Long drinkId) {
        Drink drink = drinkRepository.findById(drinkId).orElseThrow(DrinkNotFoundException::new);
        return new GetDrinkServiceResponse(drink);
    }

    public void enjoyDrink(Long userId, EnjoyDrinkServiceRequest request) {
        enjoyDrinkValidator.checkIsAlreadyEnjoyed(userId, request.getDrinkId());
        EnjoyDrink enjoyDrink = new EnjoyDrink(userId, request.getDrinkId());
        enjoyDrinkRepository.save(enjoyDrink);
    }

    public Slice<GetHotDrinksResponse> getHotDrinks(int size) {
        PageRequest pageRequest = PageRequest.of(FIXED_INDEX_OF_GETTING_HOT_DRINKS, size);
        Slice<HotDrinkData> hotDrinks = drinkRepository.getHotDrinks(pageRequest, LocalDateTime.now());
        return new SliceImpl<>(getGetHotDrinksResponses(hotDrinks), hotDrinks.getPageable(), hotDrinks.hasNext());
    }

    private List<GetHotDrinksResponse> getGetHotDrinksResponses(Slice<HotDrinkData> hotDrinks) {
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
}
