package co.kr.jurumarble.client.tourApi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TourApiController {

    private final TourApiService tourApiService;

    @GetMapping("/treatMenu")
    public String getTreatMenu(@RequestParam int contentTypeId, @RequestParam int contentId) {

        return tourApiService.getTreatMenu(contentTypeId, contentId);
    }


    @GetMapping("/imgUrl")
    public List<String> getDetailImgUrl(@RequestParam int contentId){
        
        return tourApiService.getDetailImages(contentId);
    } 
}
