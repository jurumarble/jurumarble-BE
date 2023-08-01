package co.kr.jurumarble.comment.dto.request;

import co.kr.jurumarble.comment.domain.Snack;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateSnackRequest {

    private String snackImage;

    private String snackName;

    private String restaurantName;


}
