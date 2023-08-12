package co.kr.jurumarble.comment.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateSnackRequest {

    private String restaurantName;

    private String restaurantImage;

    private String treatMenu;


}
