package co.kr.jurumarble.notification;

import co.kr.jurumarble.notification.service.NotificationService;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.vote.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationSender {
    private final NotificationService notificationService;
    private final VoteService voteService;
    private static final Logger logger = LoggerFactory.getLogger(NotificationSender.class);

    @Autowired
    public NotificationSender(NotificationService notificationService, VoteService voteService) {
        this.notificationService = notificationService;
        this.voteService = voteService;
    }

    public void sendCommentNotification(Long typeId) {
        User receiver = voteService.getVoteCreator(typeId);
        String content = "투표에 댓글이 달렸습니다.";
        String url = "/api/votes/" + typeId;

        notificationService.send(receiver, Notification.NotificationType.COMMENT, content, url);
        logger.info("Notification sent to user: {}, type: {}, content: {}, url: {}", receiver.getId(), Notification.NotificationType.COMMENT, content, url);
    }



}