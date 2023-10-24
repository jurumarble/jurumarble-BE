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
    private String content;

    @Schema(description = "Related Url")
    private String url;


    @Builder
    public CreateNotificationRequest(String title, String content, String url) {
        this.title = title;
        this.content = content;
        this.url = url;
    }

    public CreateNotificationServiceRequest toServiceRequest() {
        return CreateNotificationServiceRequest.builder()
                .title(title)
                .content(content)
                .url(url)
                .build();
    }

}
