package co.kr.jurumarble.notification.dto;

import co.kr.jurumarble.notification.domain.Notification;
import lombok.*;

public class NotificationDto {
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response {
        String id;
        String name;
        String content;
        String type;
        String createdAt;
        public static Response createResponse(Notification notification) {
            return Response.builder()
                    .content(notification.getContent())
                    .id(notification.getId().toString())
                    .name(notification.getReceiver().getNickname())
                    .createdAt(notification.getCreatedDate().toString())
                    .build();
        }
    }
}
