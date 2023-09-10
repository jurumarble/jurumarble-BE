package co.kr.jurumarble.comment.service;

import co.kr.jurumarble.client.tourApi.RestaurantInfoDto;
import co.kr.jurumarble.client.tourApi.TourApiService;
import co.kr.jurumarble.comment.enums.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TourApiDataManager {

    private final TourApiService tourApiService;


    public List<RestaurantInfoDto> getRestaurantInfoList(String keyword, Region region, int page) {
        return (keyword != null)
                ? tourApiService.getRestaurantInfoByKeyWord(keyword, region.getCode(), page)
                : tourApiService.getRestaurantInfo(region.getCode(), page);
    }

    public List<SearchRestaurantData> convertToSearchRestaurantDataList(List<RestaurantInfoDto> restaurantInfo) {
        return restaurantInfo.stream()
                .map(restaurant -> new SearchRestaurantData(
                        restaurant.getContentId(),
                        restaurant.getTitle(),
                        restaurant.getFirstImage(),
                        tourApiService.getFirstMenu(restaurant.getContentId())
                ))
                .collect(Collectors.toList());
    }

    public List<String> fetchDetailImages(String contentId) {
        List<String> detailImages = tourApiService.getDetailImages(contentId);
        return detailImages;
    }

}
