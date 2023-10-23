package co.kr.jurumarble.notification.service;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateNotificationServiceRequest {
    private String title;

    private String message;

    private String relatedUrl;

    @Builder
    public CreateNotificationServiceRequest(String title, String message, String relatedUrl) {
        this.title = title;
        this.message = message;
        this.relatedUrl = relatedUrl;
    }
}
