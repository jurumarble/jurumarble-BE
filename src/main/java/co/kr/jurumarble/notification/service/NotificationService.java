package co.kr.jurumarble.notification.service;

import co.kr.jurumarble.exception.notification.NotificationNotFoundException;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.notification.domain.Notification;
import co.kr.jurumarble.notification.dto.NotificationDto;
import co.kr.jurumarble.notification.repository.EmitterRepository;
import co.kr.jurumarble.notification.repository.NotificationRepository;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    // SSE 연결 지속 시간 설정
    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    // [1] subscribe()
    public SseEmitter subscribe(Long userId, String lastEventId) {
        String emitterId = makeTimeIncludeId(userId);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));
        String eventId = makeTimeIncludeId(userId);
        sendNotification(emitter, eventId, emitterId, "EventStream Created. [userId=" + userId + "]");
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, userId, emitterId, emitter);
        }
        return emitter;
    }

    @Transactional
    public void send(User receiver, Notification.NotificationType notificationType, String content, String url) {
        Notification notification = notificationRepository.save(createNotification(receiver, notificationType, content, url));
        String receiverId = String.valueOf(receiver.getId());
        String eventId = receiverId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiverId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, NotificationDto.from(notification));
                }
        );
    }

    private String makeTimeIncludeId(Long userId) { // (3)
        return userId + "_" + System.currentTimeMillis();
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(data)
            );
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }


    private boolean hasLostData(String lastEventId) { // (5)
        return !lastEventId.isEmpty();
    }


    private void sendLostData(String lastEventId, Long userId, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(userId));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    private Notification createNotification(User receiver, Notification.NotificationType notificationType, String content, String url) {
        return Notification.builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .content(content)
                .url(url)
                .isRead(false)
                .build();
    }

    public void sendNotificationToUser(Long userId, CreateNotificationServiceRequest request) {
        User receiver = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Notification.NotificationType type = Notification.NotificationType.ADMIN_NOTIFY;
        String message = request.getMessage();
        String relatedUrl = request.getRelatedUrl();
        send(receiver, type, message, relatedUrl);
    }

    public void sendNotificationToAllUsers(CreateNotificationServiceRequest request) {
        List<User> allUsers = userRepository.findAll();
        Notification.NotificationType type = Notification.NotificationType.ADMIN_NOTIFY;
        String message = request.getMessage();
        String relatedUrl = request.getRelatedUrl();

        allUsers.stream()
                .forEach(user -> CompletableFuture.runAsync(() -> sendAsync(user, type, message, relatedUrl)));
    }

    private void sendAsync(User receiver, Notification.NotificationType notificationType, String content, String relatedUrl) {
        send(receiver, notificationType, content, relatedUrl);
        log.info("Thread: {}, Notification sent to user: {}, type: {}, content: {}, url: {}",
                Thread.currentThread().getName(), receiver.getId(), Notification.NotificationType.COMMENT, content, relatedUrl);
    }

    public List<NotificationDto> getNotificationDtos(Long userId) {
        return getNotificationsByUserId(userId).stream()
                .map(NotificationDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void setNotificationsAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(NotificationNotFoundException::new);
        notification.setIsReadTrue();
    }

    private List<Notification> getNotificationsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return notificationRepository.findNotificationsByUser(user);
    }

}
