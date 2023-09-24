package co.kr.jurumarble.notification;

import co.kr.jurumarble.notification.service.NotificationService;
import co.kr.jurumarble.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationSender {

    private final NotificationService notificationService;

    @Autowired
    public NotificationSender(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

//    public void sendVoteNotification(User receiver, Long typeId, int pariti) {
//        String content = "";
//        String url = "/api/votes/" + typeId;
//        notificationService.send(receiver, Notification.NotificationType.VOTE, content, url);
//    }

    public void sendCommentNotification(User receiver, Long typeId) {
        String content = "투표에 댓글이 달렸어요.";
        String url = "/api/votes/" + typeId;
        notificationService.send(receiver, Notification.NotificationType.COMMENT, content, url);
    }
}
