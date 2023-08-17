package co.kr.jurumarble.drink.controller;

import co.kr.jurumarble.drink.controller.request.EnjoyDrinkRequest;
import co.kr.jurumarble.drink.controller.response.GetDrinkResponse;
import co.kr.jurumarble.drink.service.DrinkService;
import co.kr.jurumarble.drink.service.response.GetDrinkServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drinks")
public class DrinkController {

    private final DrinkService drinkService;

    @Operation(summary = "전통주 정보 조회(단건 조회)")
    @GetMapping("/{drinkId}")
    public ResponseEntity<GetDrinkResponse> getDrinkData(@PathVariable Long drinkId) {
        GetDrinkServiceResponse drinkData = drinkService.getDrinkData(drinkId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(drinkData.toControllerResponse());
    }

    @Operation(summary = "전통주 즐겼어요 추가")
    @PostMapping("/enjoys")
    public ResponseEntity<HttpStatus> enjoyDrink(@RequestAttribute Long userId, @RequestBody EnjoyDrinkRequest request) {
        drinkService.enjoyDrink(userId, request.toServiceRequest());
        return ResponseEntity.ok().build();
    }

//    @Operation(summary = "전통주 인기순 조회(핫 전통주 - 일주일동안 즐겼어요 많은 순)")
//    @GetMapping("/hot")
//    public ResponseEntity getHotDrinks() {
//        drinkService.getHotDrinks()
//    }
}
