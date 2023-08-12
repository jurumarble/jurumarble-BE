package co.kr.jurumarble.drink.controller;

import co.kr.jurumarble.drink.controller.response.GetDrinkResponse;
import co.kr.jurumarble.drink.service.DrinkService;
import co.kr.jurumarble.drink.service.response.GetDrinkServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drinks")
public class DrinkController {

    private final DrinkService drinkService;

    @GetMapping("/{drinkId}")
    public ResponseEntity<GetDrinkResponse> getDrinkData(@PathVariable Long drinkId) {
        GetDrinkServiceResponse drinkData = drinkService.getDrinkData(drinkId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(drinkData.toControllerResponse());
    }
}
