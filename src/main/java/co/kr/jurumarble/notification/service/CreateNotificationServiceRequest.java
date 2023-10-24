package co.kr.jurumarble.notification.service;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateNotificationServiceRequest {
    private String title;

    private String content;

    private String url;

    @Builder
    public CreateNotificationServiceRequest(String title, String content, String url) {
        this.title = title;
        this.content = content;
        this.url = url;
    }
}
