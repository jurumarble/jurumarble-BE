package co.kr.jurumarble.client.tourApi;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class TourApiService {

    private final TourApiClient tourApiClient;

    @Value("${tour.api.servicekey}")
    private String serviceKey;
    @Value("${tour.api.mobile.os}")
    private String mobileOS;
    @Value("${tour.api.mobile.app}")
    private String mobileApp;
    @Value("${tour.api.response.type}")
    private String responseType;
    @Value("${tour.api.content-type-id}")
    private int contentTypeId;
    @Value("${tour.api.image-yn}")
    private String imageYN;
    @Value("${tour.api.subimage-yn}")
    private String subImageYN;
    @Value("${tour.api.num-of-rows}")
    private int numOfRows;
    @Value("${tour.api.list-yn}")
    private String listYN;
    @Value("${tour.api.arrange}")
    private String arrange;
    @Value("${tour.api.cat1}")
    private String cat1;
    @Value("${tour.api.cat2}")
    private String cat2;


    public String getFirstMenu(String contentId) {
        String decodedServiceKey = decodeServiceKey(serviceKey);
        TourDetailIntroResponse introResponse = tourApiClient.getDetailIntro(
                decodedServiceKey,
                contentTypeId,
                contentId,
                mobileOS,
                mobileApp,
                responseType);

        return introResponse.getFirstMenu();
    }

    public List<String> getDetailImages(String contentId) {
        String decodedServiceKey = decodeServiceKey(serviceKey);
        TourDetailImageResponse imageResponse = tourApiClient.getDetailImages(
                decodedServiceKey,
                contentId,
                mobileOS,
                mobileApp,
                imageYN,
                subImageYN,
                numOfRows,
                responseType);


        return imageResponse.getDetailImages();
    }


    public RestaurantListDto getRestaurantInfo(Integer areaCode, int pageNo) {
        String decodedServiceKey = decodeServiceKey(serviceKey);

        TourAreaBasedListResponse restaurantList = tourApiClient.getRestaurantList(
                decodedServiceKey,
                contentTypeId,
                Optional.ofNullable(areaCode).orElse(null),
                mobileOS,
                mobileApp,
                numOfRows,
                pageNo,
                listYN,
                arrange,
                cat1,
                cat2,
                responseType);

        List<String> contentIds = restaurantList.getContentIds();
        List<String> firstImages = restaurantList.getFirstImages();
        List<String> titles = restaurantList.getTitles();
        Integer totalCount = restaurantList.getTotalCount();
        Integer page = restaurantList.getPageNo();


        List<RestaurantInfoDto> restaurantInfoList =
                IntStream.range(0, contentIds.size())
                        .mapToObj(i -> new RestaurantInfoDto(contentIds.get(i), firstImages.get(i), titles.get(i)))
                        .collect(Collectors.toList());

        return new RestaurantListDto(restaurantInfoList, totalCount, page, numOfRows);
    }


    public RestaurantListDto getRestaurantInfoByKeyWord(String keyWord, Integer areaCode, int pageNo) {
        String decodedServiceKey = decodeServiceKey(serviceKey);

        TourSearchKeyWordResponse restaurantList = tourApiClient.getRestaurantListByKeyWord(
                decodedServiceKey,
                contentTypeId,
                Optional.ofNullable(areaCode).orElse(null),
                mobileOS,
                mobileApp,
                listYN,
                numOfRows,
                pageNo,
                keyWord,
                responseType);

        List<String> contentIds = restaurantList.getContentIds();
        List<String> firstImages = restaurantList.getFirstImages();
        List<String> titles = restaurantList.getTitles();
        Integer totalCount = restaurantList.getTotalCount();
        Integer page = restaurantList.getPageNo();

        List<RestaurantInfoDto> restaurantInfoList =
                IntStream.range(0, contentIds.size())
                        .mapToObj(i -> new RestaurantInfoDto(contentIds.get(i), firstImages.get(i), titles.get(i)))
                        .collect(Collectors.toList());

        return new RestaurantListDto(restaurantInfoList, totalCount, page, numOfRows);
    }


    private String decodeServiceKey(String encodedServiceKey) {
        String decodedServiceKey = null;
        try {
            decodedServiceKey = URLDecoder.decode(encodedServiceKey, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedServiceKey;
    }


}