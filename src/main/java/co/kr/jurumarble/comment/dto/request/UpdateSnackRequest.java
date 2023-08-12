package co.kr.jurumarble.comment.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateSnackRequest {

    private String snackImage;

    private String snackName;

    private String restaurantName;


}
