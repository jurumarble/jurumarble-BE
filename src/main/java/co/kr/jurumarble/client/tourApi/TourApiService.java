package co.kr.jurumarble.client.tourApi;

import co.kr.jurumarble.exception.comment.NoDataFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

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


    public String getTreatMenu(String contentId) {
        String decodedServiceKey = decodeServiceKey(serviceKey);
        TourDetailIntroResponse introResponse = tourApiClient.getDetailIntro(
                decodedServiceKey,
                contentTypeId,
                contentId,
                mobileOS,
                mobileApp,
                responseType);

        System.out.println("API response: " + introResponse);

        return introResponse.getTreatMenu();
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


        System.out.println("API response: " + imageResponse);

        return imageResponse.getDetailImages();
    }


    public List<RestaurantInfoDto> getRestaurantInfo(int areaCode, int pageNo) {
        String decodedServiceKey = decodeServiceKey(serviceKey);

        TourAreaBasedListResponse restaurantList = tourApiClient.getRestaurantList(
                decodedServiceKey,
                contentTypeId,
                areaCode,
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

        List<RestaurantInfoDto> restaurantInfoList = new ArrayList<>();
        for (int i = 0; i < contentIds.size(); i++) {
            String contentId = contentIds.get(i);
            String firstImage = firstImages.get(i);
            String title = titles.get(i);
            restaurantInfoList.add(new RestaurantInfoDto(contentId, firstImage, title));
        }

        System.out.println("API response: " + restaurantInfoList);

        return restaurantInfoList;
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