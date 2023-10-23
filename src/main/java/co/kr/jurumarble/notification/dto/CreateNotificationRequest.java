package co.kr.jurumarble.notification.dto;

import co.kr.jurumarble.notification.service.CreateNotificationServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateNotificationRequest {

    @Schema(description = "알림 제목")
    private String title;

    @Schema(description = "알림 내용")
    private String message;

    @Schema(description = "Related Url")
    private String relatedUrl;


    @Builder
    public CreateNotificationRequest(String title, String message, String relatedUrl) {
        this.title = title;
        this.message = message;
        this.relatedUrl = relatedUrl;
    }

    public CreateNotificationServiceRequest toServiceRequest() {
        return CreateNotificationServiceRequest.builder()
                .title(title)
                .message(message)
                .relatedUrl(relatedUrl)
                .build();
    }

}
