package co.kr.jurumarble.notification.controller;

import co.kr.jurumarble.notification.domain.Notification;
import co.kr.jurumarble.notification.dto.CreateNotificationRequest;
import co.kr.jurumarble.notification.dto.NotificationDto;
import co.kr.jurumarble.notification.service.NotificationSender;
import co.kr.jurumarble.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/notifications")
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "notification", description = "notification api")
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "알림 구독", description = "알림을 위한 SSE 구독")
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@RequestAttribute Long userId, @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return notificationService.subscribe(userId, lastEventId);
    }

    @Operation(summary = "단일 사용자 알림 전송", description = "특정 사용자에게 알림 메시지를 전송합니다.")
    @PostMapping("/users/{userId}")
    public ResponseEntity sendToUser(@PathVariable Long userId, @RequestBody @Valid CreateNotificationRequest createNotificationRequest) {
        notificationService.sendNotificationToUser(userId, createNotificationRequest.toServiceRequest());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "전체 사용자 알림 전송", description = "모든 사용자에게 알림 메시지를 전송합니다.")
    @PostMapping("/users")
    public ResponseEntity<?> sendToAllUsers(@RequestBody @Valid CreateNotificationRequest createNotificationRequest) {
        notificationService.sendNotificationToAllUsers(createNotificationRequest.toServiceRequest());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "알림 조회", description = "사용자의 모든 알림 메시지를 조회합니다.")
    @GetMapping("")
    public ResponseEntity<List<NotificationDto>> getNotifications(@RequestAttribute Long userId) {
        List<NotificationDto> notifications = notificationService.getNotificationDtos(userId);
        return new ResponseEntity(notifications, HttpStatus.OK);
    }

    @Operation(summary = "알림 읽음 처리", description = "사용자가 클릭한 알림을 읽음 처리 합니다.")
    @PostMapping("/{notificationId}/read")
    public ResponseEntity setNotificationAsRead(@PathVariable Long notificationId) {
        notificationService.setNotificationsAsRead(notificationId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
