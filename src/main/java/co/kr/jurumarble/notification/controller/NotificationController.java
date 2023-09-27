package co.kr.jurumarble.notification.controller;

import co.kr.jurumarble.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequestMapping("/api/notifications")
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "notification", description = "notification api")
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "Subscribe to notifications", description = "SSE subscription for notifications")
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@RequestAttribute Long userId, @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return notificationService.subscribe(userId, lastEventId);
    }

}
