package co.kr.jurumarble.client.tourApi;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

    @Value("${tour.api.image.enabled}")
    private String imageYN;
    @Value("${tour.api.subimage.enabled}")
    private String subImageYN;
    @Value("${tour.api.num-of-rows}")
    private int numOfRows;

    public String getTreatMenu(int contentTypeId, int contentId) {
        String decodedServiceKey = decodeServiceKey(serviceKey);
        TourIntroResponse introResponse = tourApiClient.getDetailIntro(
                decodedServiceKey,
                contentTypeId,
                contentId,
                mobileOS,
                mobileApp,
                responseType);

        System.out.println("API response: " + introResponse);

        return introResponse.getTreatMenu();
    }

    public List<String> getDetailImages(int contentId) {
        String decodedServiceKey = decodeServiceKey(serviceKey);
        TourImageResponse imageResponse = tourApiClient.getDetailImages(
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