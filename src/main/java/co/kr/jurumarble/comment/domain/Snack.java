package co.kr.jurumarble.comment.domain;

import co.kr.jurumarble.comment.dto.request.UpdateSnackRequest;
import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Snack {

    private String snackImage;

    private String snackName;

    private String restaurantName;

    public void updateSnackImage(String snackImage) {
        this.snackImage = snackImage;
    }

    public void updateSnackName(String snackName) {
        this.snackName = snackName;
    }

    public void updateRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }


    public Snack(UpdateSnackRequest updateSnackRequest){
        this.snackImage = updateSnackRequest.getSnackImage();
        this.snackName = updateSnackRequest.getSnackName();
        this.restaurantName = updateSnackRequest.getRestaurantName();
    }

}

