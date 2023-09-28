package co.kr.jurumarble.notification.service;

import co.kr.jurumarble.notification.domain.Notification;
import co.kr.jurumarble.notification.event.CommentCreatedEvent;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteResult;
import co.kr.jurumarble.vote.repository.VoteRepository;
import co.kr.jurumarble.vote.repository.VoteResultRepository;
import co.kr.jurumarble.notification.event.DoVoteEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationSender {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final VoteResultRepository voteResultRepository;
    private final NotificationService notificationService;
    private static final Logger logger = LoggerFactory.getLogger(NotificationSender.class);


    @TransactionalEventListener
    @Async
    public void handleVoteCreated(CommentCreatedEvent event) {
        sendNotificationForNewComments(event.getVoteId());
    }

    @TransactionalEventListener
    @Async
    public void handleDoVote(DoVoteEvent event) {
        sendNotificationsForVoters(event.getVoteId());
    }

    public void sendNotificationForNewComments(Long voteId) {
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        User receiver = userRepository.findById(vote.getPostedUserId()).orElseThrow(UserNotFoundException::new);
        String content = "투표에 댓글이 달렸습니다.";
        String url = "/api/votes/" + voteId;
        notificationService.send(receiver, Notification.NotificationType.COMMENT, content, url);
        logger.info("Notification sent to user: {}, type: {}, content: {}, url: {}", receiver.getId(), Notification.NotificationType.COMMENT, content, url);
    }

    public void sendNotificationsForVoters(Long voteId) {
        Long count = voteResultRepository.countByVoteId(voteId);
        if (count % 10 == 0 && count != 0) {
            String content = "투표에 " + count + "명 이상이 참여했어요!";
            String url = "/api/votes/" + voteId;
            List<VoteResult> voteResultList = voteResultRepository.findByVoteId(voteId);
            sendNotificationsToVoters(content, url, voteResultList);
        }
    }

    private void sendNotificationsToVoters(String content, String url, List<VoteResult> voteResultList) {
        for (VoteResult result : voteResultList) {
            User receiver = userRepository.findById(result.getVotedUserId()).orElseThrow(UserNotFoundException::new);
            notificationService.send(receiver, Notification.NotificationType.VOTE, content, url);
            logger.info("Notification sent to user: {}, type: {}, content: {}, url: {}", receiver.getId(), Notification.NotificationType.VOTE, content, url);
        }
    }

}