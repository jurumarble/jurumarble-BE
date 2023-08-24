package co.kr.jurumarble.comment.service;

import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.repository.CommentRepository;
import co.kr.jurumarble.comment.service.request.CreateCommentServiceRequest;
import co.kr.jurumarble.comment.service.request.UpdateCommentServiceRequest;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.repository.VoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private VoteRepository voteRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentValidator commentValidator;

    @DisplayName("댓글을 생성한다.")
    @Test
    void createComment() {
        // given
        Long userId = 1L;
        Long voteId = 1L;

        User user = User.builder()
                .age(30)
                .build();

        Vote vote = new Vote();

        CreateCommentServiceRequest request = CreateCommentServiceRequest.builder()
                .content("댓글 내용")
                .parentId(null)
                .build();

        Comment parentComment = null;
        Comment comment = new Comment(request, parentComment, user, voteId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(voteRepository.findById(voteId)).thenReturn(Optional.of(vote));
        when(commentValidator.checkParentComment(request)).thenReturn(parentComment);
        doNothing().when(commentValidator).checkNestedCommentAllowed(parentComment);

        // when
        commentService.createComment(voteId, userId, request);

        // then
        verify(userRepository, times(1)).findById(userId);
        verify(voteRepository, times(1)).findById(voteId);
        verify(commentValidator, times(1)).checkParentComment(request);
        verify(commentValidator, times(1)).checkNestedCommentAllowed(parentComment);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }


    @DisplayName("댓글을 업데이트한다.")
    @Test
    void updateComment() {
        // given
        Long userId = 1L;
        Long voteId = 1L;
        Long commentId = 1L;

        User user = User.builder()
                .build();

        Vote vote = new Vote();

        Comment comment = new Comment();

        UpdateCommentServiceRequest request = UpdateCommentServiceRequest.builder()
                .content("변경된 댓글 내용")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(voteRepository.findById(voteId)).thenReturn(Optional.of(vote));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        doNothing().when(commentValidator).validateCommentBelongsToUser(comment, user);

        // when
        commentService.updateComment(voteId, commentId, userId, request);

        // then
        verify(userRepository, times(1)).findById(userId);
        verify(voteRepository, times(1)).findById(voteId);
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentValidator, times(1)).validateCommentBelongsToUser(comment, user);
        assertEquals(comment.getContent(), request.getContent());
    }
}