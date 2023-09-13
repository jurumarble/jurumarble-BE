package co.kr.jurumarble.comment.service;

import co.kr.jurumarble.client.tourApi.RestaurantListDto;
import co.kr.jurumarble.client.tourApi.TourApiService;
import co.kr.jurumarble.comment.enums.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TourApiDataManager {

    private final TourApiService tourApiService;


    public RestaurantListDto getRestaurantInfoList(String keyword, Region region, int page) {
        return (keyword != null)
                ? tourApiService.getRestaurantInfoByKeyWord(keyword, region.getCode(), page)
                : tourApiService.getRestaurantInfo(region.getCode(), page);
    }

    public Page<SearchRestaurantData> convertToSearchRestaurantDataList(RestaurantListDto restaurantListDto) {
        List<SearchRestaurantData> searchRestaurants = restaurantListDto.getRestaurantInfoList().stream()
                .map(restaurant -> new SearchRestaurantData(
                        restaurant.getContentId(),
                        restaurant.getTitle(),
                        restaurant.getFirstImage(),
                        tourApiService.getFirstMenu(restaurant.getContentId())
                ))
                .collect(Collectors.toList());
        System.out.println("searchRestaurants = " + searchRestaurants);

        return new PageImpl<>(searchRestaurants, PageRequest.of(restaurantListDto.getPage() - 1, restaurantListDto.getNumOfRows()), restaurantListDto.getTotalCount());
    }

    public List<String> fetchDetailImages(String contentId) {
        List<String> detailImages = tourApiService.getDetailImages(contentId);
        return detailImages;
    }

}
