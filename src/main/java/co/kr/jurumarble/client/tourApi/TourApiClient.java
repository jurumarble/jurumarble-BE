package co.kr.jurumarble.client.tourApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "tour-service", url = "${tour.api.url}")
public interface TourApiClient {





    //소개 정보
    @GetMapping(value = "/detailIntro1")
    TourIntroResponse getDetailIntro(@RequestParam("ServiceKey") String serviceKey,
                                     @RequestParam("contentTypeId") int contentTypeId,
                                     @RequestParam("contentId") int contentId,
                                     @RequestParam("MobileOS") String mobileOS,
                                     @RequestParam("MobileApp") String mobileApp,
                                     @RequestParam("_type") String responseType);




    @GetMapping(value = "/detailImage1")
    TourImageResponse getDetailImage(@RequestParam("ServiceKey") String serviceKey,
                                     @RequestParam("contentTypeId") int contentTypeId,
                                     @RequestParam("contentId") int contentId,
                                     @RequestParam("MobileOS") String mobileOS,
                                     @RequestParam("MobileApp") String mobileApp,
                                     @RequestParam("_type") String responseType);




}
