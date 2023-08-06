package co.kr.jurumarble.client.tourApi;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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