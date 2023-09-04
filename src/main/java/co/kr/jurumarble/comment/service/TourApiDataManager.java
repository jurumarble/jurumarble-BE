package co.kr.jurumarble.comment.service;

import co.kr.jurumarble.client.tourApi.RestaurantInfoDto;
import co.kr.jurumarble.client.tourApi.TourApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TourApiDataManager {

    private final TourApiService tourApiService;


    public List<RestaurantInfoDto> getRestaurantInfoList(String keyword, Integer areaCode, int page) {
        return (keyword != null)
                ? tourApiService.getRestaurantInfoByKeyWord(keyword, areaCode, page)
                : tourApiService.getRestaurantInfo(areaCode, page);
    }

    public List<SearchRestaurantData> convertToSearchRestaurantDataList(List<RestaurantInfoDto> restaurantInfo) {
        return restaurantInfo.stream()
                .map(restaurant -> new SearchRestaurantData(
                        restaurant.getContentId(),
                        restaurant.getTitle(),
                        restaurant.getFirstImage(),
                        tourApiService.getTreatMenu(restaurant.getContentId())
                ))
                .collect(Collectors.toList());
    }

    public List<String> fetchDetailImages(String contentId) {
        List<String> detailImages = tourApiService.getDetailImages(contentId);
        return detailImages;
    }

}
