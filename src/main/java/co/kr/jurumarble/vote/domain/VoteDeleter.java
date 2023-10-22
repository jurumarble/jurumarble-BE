package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.bookmark.domain.Bookmark;
import co.kr.jurumarble.bookmark.repository.BookmarkRepository;
import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.repository.CommentRepository;
import co.kr.jurumarble.exception.vote.VoteContentNotFoundException;
import co.kr.jurumarble.exception.vote.VoteDrinkContentNotFoundException;
import co.kr.jurumarble.notification.domain.Notification;
import co.kr.jurumarble.notification.repository.NotificationRepository;
import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.repository.VoteContentRepository;
import co.kr.jurumarble.vote.repository.VoteDrinkContentRepository;
import co.kr.jurumarble.vote.repository.VoteResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VoteDeleter {

    private final VoteDrinkContentRepository voteDrinkContentRepository;
    private final VoteContentRepository voteContentRepository;
    private final CommentRepository commentRepository;
    private final VoteResultRepository voteResultRepository;
    private final BookmarkRepository bookmarkRepository;
    private final NotificationRepository notificationRepository;
    public void deleteVoteRelatedData(Vote vote) {
        deleteVoteContent(vote);
        deleteVoteComment(vote);
        deleteVoteResult(vote);
        deleteVoteBookmark(vote);
        deleteVoteNotification(vote);
    }

    private void deleteVoteContent(Vote vote) {
        if (VoteType.DRINK == vote.getVoteType()) {
            VoteDrinkContent voteDrinkContent = voteDrinkContentRepository.findByVoteId(vote.getId()).orElseThrow(VoteDrinkContentNotFoundException::new);
            voteDrinkContentRepository.delete(voteDrinkContent);
        }

        if (VoteType.NORMAL == vote.getVoteType()) {
            VoteContent voteContent = voteContentRepository.findByVoteId(vote.getId()).orElseThrow(VoteContentNotFoundException::new);
            voteContentRepository.delete(voteContent);
        }
    }

    private void deleteVoteComment(Vote vote) {
        List<Comment> comments = commentRepository.findByVoteId(vote.getId());
        commentRepository.deleteAll(comments);
    }

    private void deleteVoteResult(Vote vote) {
        List<VoteResult> voteResults = voteResultRepository.findByVoteId(vote.getId());
        voteResultRepository.deleteAll(voteResults);
    }

    private void deleteVoteBookmark(Vote vote) {
        List<Bookmark> bookmarks = bookmarkRepository.findByVoteId(vote.getId());
        bookmarkRepository.deleteAll(bookmarks);
    }

    private void deleteVoteNotification(Vote vote) {
        List<Notification> notifications = notificationRepository.findNotificationsByUrl(vote.getId().toString());
        notificationRepository.deleteAll(notifications);
    }
}
