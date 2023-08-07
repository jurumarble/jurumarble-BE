package co.kr.jurumarble.client.tourApi;

import lombok.RequiredArgsConstructor;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TourApiController {

    private final TourApiService tourApiService;

    @GetMapping("/treatMenu")
    public String getTreatMenu(@RequestParam String contentId) {

        return tourApiService.getTreatMenu( contentId);
    }


    @GetMapping("/imgUrl")
    public List<String> getDetailImgUrl(@RequestParam String contentId){

        return tourApiService.getDetailImages(contentId);
    }

    @GetMapping("/restaurantInfo")
    public List<RestaurantInfoDto> getRestaurantInfo(@RequestParam int areaCode, @RequestParam int pageNo){

        return tourApiService.getRestaurantInfo(areaCode, pageNo);
    }

}
