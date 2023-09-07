package co.kr.jurumarble.comment.service.request;

import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.enums.CommentType;
import co.kr.jurumarble.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateCommentServiceRequest {
    private Long parentId;
    private String content;

    @Builder
    public CreateCommentServiceRequest(Long parentId, String content) {
        this.parentId = parentId;
        this.content = content;
    }


    public Comment toComment(CommentType commentType, Comment parentComment, User user, Long typeId) {
        Comment.CommentBuilder commentBuilder = Comment.builder()
                .user(user)
                .content(content)
                .age(user.classifyAge())
                .mbti(user.getMbti())
                .gender(user.getGender())
                .parent(parentComment);

        if (commentType == CommentType.VOTES) {
            commentBuilder.voteId(typeId);
        } else if (commentType == CommentType.DRINKS) {
            commentBuilder.drinkId(typeId);
        }

        return commentBuilder.build();
    }
}
