package co.kr.jurumarble.comment.dto.request;

import co.kr.jurumarble.comment.service.request.UpdateRestaurantServiceRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateRestaurantRequest {
    @NotNull
    private String restaurantName;
    private String restaurantImage;

    @Builder
    public UpdateRestaurantRequest(String restaurantName, String restaurantImage) {
        this.restaurantName = restaurantName;
        this.restaurantImage = restaurantImage;
    }

    public UpdateRestaurantServiceRequest toServiceRequest() {
        return UpdateRestaurantServiceRequest.builder()
                .restaurantName(restaurantName)
                .restaurantImage(restaurantImage)
                .build();
    }
}
