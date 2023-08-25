package co.kr.jurumarble.drink.controller;

import co.kr.jurumarble.drink.controller.request.EnjoyDrinkRequest;
import co.kr.jurumarble.drink.controller.response.GetDrinkResponse;
import co.kr.jurumarble.drink.controller.response.GetHotDrinksResponse;
import co.kr.jurumarble.drink.service.DrinkService;
import co.kr.jurumarble.drink.service.response.GetDrinkServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drinks")
public class DrinkController {

    private final DrinkService drinkService;

    @Operation(summary = "전통주 정보 조회(단건 조회)", description = "쿼리 파라미터로 전통주 아이디를 넣어서 보내주세요. 예시) /api/drinks/100")
    @GetMapping("/{drinkId}")
    public ResponseEntity<GetDrinkResponse> getDrinkData(@PathVariable Long drinkId) {
        GetDrinkServiceResponse drinkData = drinkService.getDrinkData(drinkId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(drinkData.toControllerResponse());
    }

    @Operation(summary = "전통주 즐겼어요 추가", description = "요청시 유저 토큰과 바디값을 잘 확인해서 보내주세요.")
    @PostMapping("/enjoys")
    public ResponseEntity<HttpStatus> enjoyDrink(@RequestAttribute Long userId, @RequestBody EnjoyDrinkRequest request) {
        drinkService.enjoyDrink(userId, request.toServiceRequest());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "전통주 인기순 조회(핫 전통주 10 - 일주일동안 즐겼어요 많은 순)", description = "항상 10개의 데이터가 고정으로 나오고 데이터가 부족하면 즐겼어요 누적으로 나옵니다.")
    @GetMapping("/hot")
    public ResponseEntity<List<GetHotDrinksResponse>> getHotDrinks() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(drinkService.getHotDrinks());
    }
}
