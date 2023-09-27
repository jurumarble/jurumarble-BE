package co.kr.jurumarble.notification.service;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateNotificationServiceRequest {

    private String message;

    private String relatedUrl;

    @Builder
    public CreateNotificationServiceRequest(String message, String relatedUrl) {
        this.message = message;
        this.relatedUrl = relatedUrl;
    }
}
