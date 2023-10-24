package co.kr.jurumarble.notification.dto;

import co.kr.jurumarble.notification.domain.Notification;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationDto {
    Long id;

    String url;

    String content;

    Notification.NotificationType type;

    Boolean isRead;

    LocalDateTime createdAt;

    @Builder
    public NotificationDto(Long id, String url, String content, Notification.NotificationType type, Boolean isRead, LocalDateTime createdAt) {
        this.id = id;
        this.url = url;
        this.content = content;
        this.type = type;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }


    public static NotificationDto from(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .url(notification.getUrl())
                .content(notification.getContent())
                .type(notification.getNotificationType())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedDate()).build();
    }
}
