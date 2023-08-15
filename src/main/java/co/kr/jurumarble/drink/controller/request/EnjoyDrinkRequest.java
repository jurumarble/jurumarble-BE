package co.kr.jurumarble.drink.controller.request;

import co.kr.jurumarble.drink.service.request.EnjoyDrinkServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnjoyDrinkRequest {

    @Schema(description = "전통주 아이디", example = "57")
    @Positive(message = "전통주 아이디는 양의 정수여야 합니다.")
    private Long drinkId;


    public EnjoyDrinkServiceRequest toServiceRequest() {
        return new EnjoyDrinkServiceRequest(drinkId);
    }
}
