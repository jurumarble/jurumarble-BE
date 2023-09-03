package co.kr.jurumarble.drink.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetDrinkResponse {

    @Schema(description = "전통주 아이디", example = "102")
    private Long drinkId;

    @Schema(description = "전통주 이름", example = "꿀 막걸리")
    private String name;

    @Schema(description = "전통주 타입", example = "막걸리")
    private String type;

    @Schema(description = "상품 이름", example = "진도 꿀 막걸리")
    private String productName;

    @Schema(description = "전통주 도수", example = "4%~6%")
    private String alcoholicBeverage;

    @Schema(description = "전통주 원자재", example = "밤,쌀,...")
    private String rawMaterial;

    @Schema(description = "전통주 용량(ml)", example = "500")
    private String capacity;

    @Schema(description = "제조 주소", example = "진라남도 진도군...")
    private String manufactureAddress;

    @Schema(description = "전통주 사진", example = "https://shopping-phinf.pstatic.net/main_2013736/20137360521.20190709103019.jpg")
    private String image;

    @Builder
    private GetDrinkResponse(Long drinkId, String name, String type, String productName, String alcoholicBeverage, String rawMaterial, String capacity, String manufactureAddress, String image) {
        this.drinkId = drinkId;
        this.name = name;
        this.type = type;
        this.productName = productName;
        this.alcoholicBeverage = alcoholicBeverage;
        this.rawMaterial = rawMaterial;
        this.capacity = capacity;
        this.manufactureAddress = manufactureAddress;
        this.image = image;
    }
}
