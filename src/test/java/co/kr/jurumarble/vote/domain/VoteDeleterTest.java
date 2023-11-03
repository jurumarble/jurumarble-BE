package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.bookmark.domain.Bookmark;
import co.kr.jurumarble.bookmark.repository.BookmarkRepository;
import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.repository.CommentRepository;
import co.kr.jurumarble.notification.domain.Notification;
import co.kr.jurumarble.notification.repository.NotificationRepository;
import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.repository.VoteContentRepository;
import co.kr.jurumarble.vote.repository.VoteDrinkContentRepository;
import co.kr.jurumarble.vote.repository.VoteResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VoteDeleterTest {

    @InjectMocks
    private VoteDeleter voteDeleter;

    @Mock
    private VoteDrinkContentRepository voteDrinkContentRepository;

    @Mock
    private VoteContentRepository voteContentRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private VoteResultRepository voteResultRepository;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("술 투표를 삭제하면 해당 술 후보 컨텐츠를 삭제한다.")
    @Test
    public void testDeleteVoteContentForDrinkVote() {

        // given
        Long voteId = 1L;
        Vote vote = Vote.builder()
                .voteId(voteId)
                .voteType(VoteType.DRINK)
                .build();
        Long voteDrinkId = 1L;
        Long voteDrinkContentAId = 2L;
        Long voteDrinkContentBId = 3L;
        VoteDrinkContent voteDrinkContent = VoteDrinkContent.builder()
                .id(voteDrinkId)
                .drinkAId(voteDrinkContentAId)
                .drinkBId(voteDrinkContentBId)
                .build();

        Mockito.when(voteDrinkContentRepository.findByVoteId(vote.getId())).thenReturn(Optional.of(voteDrinkContent));

        // when
        voteDeleter.deleteVoteContent(vote);
        // then
        Mockito.verify(voteDrinkContentRepository).delete(voteDrinkContent);
    }

    @DisplayName("일반 투표를 삭제하면 해당 후보 컨텐츠를 삭제한다.")
    @Test
    public void testDeleteVoteContentForNormalVote() {
        // given
        Long voteId = 2L;
        Vote vote = Vote.builder()
                .voteId(voteId)
                .voteType(VoteType.NORMAL)
                .build();
        Long voteContentId = 2L;
        VoteContent voteContent = VoteContent.builder()
                .id(voteContentId)
                .build();

        Mockito.when(voteContentRepository.findByVoteId(vote.getId())).thenReturn(Optional.of(voteContent));
        // when
        voteDeleter.deleteVoteContent(vote);
        // then
        Mockito.verify(voteContentRepository).delete(voteContent);
    }

    @DisplayName("투표를 삭제하면 해당 댓글을 삭제한다")
    @Test
    public void testDeleteVoteComment() {
        // given
        Long voteId = 3L;
        Vote vote = Vote.builder()
                .voteId(voteId)
                .build();
        Long commentId = 3L;
        Comment comment = Comment.builder()
                .commentId(commentId)
                .build();

        List<Comment> comments = Collections.singletonList(comment);

        Mockito.when(commentRepository.findByVoteId(vote.getId())).thenReturn(comments);
        // when
        voteDeleter.deleteVoteComment(vote);
        // then
        Mockito.verify(commentRepository).deleteAll(comments);
    }

    @DisplayName("투표를 삭제하면 해당 투표 결과를 삭제한다")
    @Test
    public void testDeleteVoteResult() {
        // given
        Long voteId = 4L;
        Vote vote = Vote.builder()
                .voteId(voteId)
                .build();

        List<VoteResult> voteResults = Collections.singletonList(new VoteResult());

        Mockito.when(voteResultRepository.findByVoteId(vote.getId())).thenReturn(voteResults);
        // when
        voteDeleter.deleteVoteResult(vote);
        // then
        Mockito.verify(voteResultRepository).deleteAll(voteResults);
    }

    @DisplayName("투표를 삭제하면 해당 투표의 북마크를 삭제한다")
    @Test
    public void testDeleteVoteBookmark() {
        // given
        Long voteId = 5L;
        Vote vote = Vote.builder()
                .voteId(voteId)
                .build();

        List<Bookmark> bookmarks = Collections.singletonList(new Bookmark());
        // when
        Mockito.when(bookmarkRepository.findByVoteId(vote.getId())).thenReturn(bookmarks);
        // then
        voteDeleter.deleteVoteBookmark(vote);

        Mockito.verify(bookmarkRepository).deleteAll(bookmarks);
    }

    @DisplayName("투표를 삭제하면 해당 투표의 알림을 삭제한다")
    @Test
    public void testDeleteVoteNotification() {
        // given
        Long voteId = 6L;
        Vote vote = Vote.builder()
                .voteId(voteId)
                .build();
        Long notificationId = 6L;
        Notification notification = Notification.builder()
                .notificationId(notificationId)
                .build();


        List<Notification> notifications = Collections.singletonList(notification);

        Mockito.when(notificationRepository.findNotificationsByUrl(vote.getId().toString())).thenReturn(notifications);
        // when
        voteDeleter.deleteVoteNotification(vote);
        // then
        Mockito.verify(notificationRepository).deleteAll(notifications);
    }
}