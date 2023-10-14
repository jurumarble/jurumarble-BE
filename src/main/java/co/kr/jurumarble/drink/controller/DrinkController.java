package co.kr.jurumarble.drink.controller;

import co.kr.jurumarble.comment.enums.Region;
import co.kr.jurumarble.drink.controller.request.*;
import co.kr.jurumarble.drink.controller.response.GetDrinkResponse;
import co.kr.jurumarble.drink.controller.response.GetEnjoyedResponse;
import co.kr.jurumarble.drink.controller.response.GetHotDrinksResponse;
import co.kr.jurumarble.drink.controller.response.GetMapInDrinksResponse;
import co.kr.jurumarble.drink.service.DrinkService;
import co.kr.jurumarble.drink.service.response.GetDrinkServiceResponse;
import co.kr.jurumarble.vote.enums.SortByType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drinks")
@Tag(name = "drink", description = "drink api")
public class DrinkController {

    private final DrinkService drinkService;

    @Operation(summary = "전통주 정보 조회(단건 조회)", description = "쿼리 파라미터로 전통주 아이디를 넣어서 보내주세요. 예시) /api/drinks/100")
    @GetMapping("/{drinkId}")
    public ResponseEntity<GetDrinkResponse> getDrinkData(@PathVariable Long drinkId) {
        GetDrinkServiceResponse drinkData = drinkService.getDrinkData(drinkId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(drinkData.toControllerResponse());
    }

    @Operation(summary = "전통주 즐겼어요", description = "요청시 유저 토큰과 바디값을 잘 확인해서 보내주세요. 북마크 여부 알아내서 생성, 삭제")
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

    @Operation(summary = "전통주 리스트 조회", description = "파라미터에 keyeword, sortBy, page, size, category 보내주시면 됩니다. 검색이 아니면 keyword = 에 값 없이 보내주시고, 필터로 지역을 추가해서 조회 가능하고 지역없이 조회하면 전체 전통주 이름순으로 나옵니다. 기획상 현재 이름순, 인기순 만")
    @GetMapping("")
    public ResponseEntity<Slice<DrinkData>> getDrinks(@RequestParam(required = false) String keyword, @RequestParam(required = false) Region region, @RequestParam SortByType sortBy, @RequestParam int page, @RequestParam int size) {
        String regionName = (region != null) ? region.getName() : null;
        Slice<DrinkData> drinkList = drinkService.getDrinks(keyword, regionName, sortBy, page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(drinkList);
    }

    @Operation(summary = "즐긴 술 리스트 조회", description = "파라미터에 keyeword, sortBy, page, size, category 보내주시면 됩니다. 검색이 아니면 keyword = 에 값 없이 보내주시고, 필터로 지역을 추가해서 조회 가능하고 지역없이 조회하면 전체 전통주 이름순으로 나옵니다.")
    @GetMapping("/enjoys")
    public ResponseEntity<Slice<DrinkData>> getEnjoyDrinks(@RequestAttribute Long userId, @RequestParam(required = false) Region region, @RequestParam int page, @RequestParam int size) {
        String regionName = (region != null) ? region.getName() : null;
        Slice<DrinkData> drinkList = drinkService.getEnjoyDrinks(userId, regionName, page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(drinkList);
    }

    @Operation(summary = "전통주 지도 조회", description = "요청시 쿼리 파라미터로 좌측상단 좌표, 우측상단 좌표 보내주세요")
    @GetMapping("/map")
    public ResponseEntity<Slice<GetMapInDrinksResponse>> getMapInDrinks(@RequestParam Double startX, @RequestParam Double startY, @RequestParam Double endX, @RequestParam Double endY, @RequestParam int page, @RequestParam int size) {
        Slice<GetMapInDrinksResponse> drinks = drinkService.getMapInDrinks(startX, startY, endX, endY, page, size);
        return new ResponseEntity(drinks, HttpStatus.OK);
    }

    @Operation(summary = "전통주 즐겼어요 여부 조회", description = "")
    @GetMapping("/{drinkId}/enjoy")
    public ResponseEntity<GetEnjoyedResponse> checkEnjoyed(@PathVariable Long drinkId, @RequestAttribute Long userId) {
        boolean result = drinkService.checkEnjoyed(drinkId, userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetEnjoyedResponse(result));
    }

    @Operation(summary = "전통주 이미지 추가 테스트")
    @PostMapping("/test")
    public ResponseEntity<HttpStatus> addDrinkImage(@RequestBody AddImage image) {
        drinkService.addDrinkImage(image);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "전통주 추가 테스트")
    @PostMapping("/add-test")
    public ResponseEntity<Long> addDrink(@RequestBody AddDrink drink) {
        Long id = drinkService.addDrink(drink);
        return new ResponseEntity(id, HttpStatus.OK);
    }

    @Operation(summary = "전통주 수정 테스트")
    @PutMapping("/update-test")
    public ResponseEntity<Long> updateDrink(@RequestBody UpdateDrink drink) {
        Long id = drinkService.updateDrink(drink);
        return new ResponseEntity(id, HttpStatus.OK);
    }


}
