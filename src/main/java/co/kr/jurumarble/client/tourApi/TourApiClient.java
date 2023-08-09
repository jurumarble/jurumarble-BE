package co.kr.jurumarble.client.tourApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "tour-service", url = "${tour.api.url}")
public interface TourApiClient {

    //소개 정보 조회
    @GetMapping(value = "/detailIntro1")
    TourDetailIntroResponse getDetailIntro(@RequestParam("ServiceKey") String serviceKey,
                                           @RequestParam("contentTypeId") int contentTypeId,
                                           @RequestParam("contentId") String contentId,
                                           @RequestParam("MobileOS") String mobileOS,
                                           @RequestParam("MobileApp") String mobileApp,
                                           @RequestParam("_type") String responseType);


    //이미지 정보 조회
    @GetMapping(value = "/detailImage1")
    TourDetailImageResponse getDetailImages(@RequestParam("ServiceKey") String serviceKey,
                                            @RequestParam("contentId") String contentId,
                                            @RequestParam("MobileOS") String mobileOS,
                                            @RequestParam("MobileApp") String mobileApp,
                                            @RequestParam("imageYN") String imageYN,
                                            @RequestParam("subImageYN") String subImageYN,
                                            @RequestParam("numOfRows") int numOfRows,
                                            @RequestParam("_type") String responseType);


    //지역 기반 정보 조회
    @GetMapping(value = "/areaBasedList1")
    TourAreaBasedListResponse getRestaurantList(@RequestParam("ServiceKey") String serviceKey,
                                                @RequestParam("contentTypeId") int contentTypeId,
                                                @RequestParam("areaCode") int areaCode,
                                                @RequestParam("MobileOS") String mobileOS,
                                                @RequestParam("MobileApp") String mobileApp,
                                                @RequestParam("numOfRows") int numOfRows,
                                                @RequestParam("pageNo") int pageNo,
                                                @RequestParam("listYN") String listYN,
                                                @RequestParam("arrange") String arrange,
                                                @RequestParam("cat1") String cat1,           //대분류:음식
                                                @RequestParam("cat2") String cat2,           //중분류:음식점
                                                @RequestParam("_type") String responseType);



    @GetMapping(value = "searchKeyword1?")
    TourSearchKeyWordResponse getRestaurantList(@RequestParam("ServiceKey") String serviceKey,
                                                @RequestParam("contentTypeId") int contentTypeId,
                                                @RequestParam("areaCode") int areaCode,
                                                @RequestParam("MobileOS") String mobileOS,
                                                @RequestParam("MobileApp") String mobileApp,
                                                @RequestParam("listYN") String listYN,
                                                @RequestParam("numOfRows") int numOfRows,
                                                @RequestParam("pageNo") int pageNo,
                                                @RequestParam("keyword") String keyWord,
                                                @RequestParam("_type") String responseType);

}
