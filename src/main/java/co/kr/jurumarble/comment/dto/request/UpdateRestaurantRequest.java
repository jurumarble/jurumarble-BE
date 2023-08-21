package co.kr.jurumarble.comment.dto.request;

import co.kr.jurumarble.comment.service.request.UpdateCommentServiceRequest;
import co.kr.jurumarble.comment.service.request.UpdateRestaurantServiceRequest;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateRestaurantRequest {
    private String restaurantName;
    private String restaurantImage;
    private String treatMenu;

    @Builder
    public UpdateRestaurantRequest(String restaurantName, String restaurantImage, String treatMenu) {
        this.restaurantName = restaurantName;
        this.restaurantImage = restaurantImage;
        this.treatMenu = treatMenu;
    }

    public UpdateRestaurantServiceRequest toServiceRequest() {
        return UpdateRestaurantServiceRequest.builder()
                .restaurantName(restaurantName)
                .restaurantImage(restaurantImage)
                .treatMenu(treatMenu)
                .build();
    }
}
