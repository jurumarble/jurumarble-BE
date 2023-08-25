package co.kr.jurumarble.comment.service.request;

import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreateCommentServiceRequest {
    private Long parentId;
    private String content;

    @Builder
    public CreateCommentServiceRequest(Long parentId, String content) {
        this.parentId = parentId;
        this.content = content;
    }


    public Comment toComment(Comment parentComment, User user, Long voteId) {
        return Comment.builder()
                .user(user)
                .voteId(voteId)
                .content(content)
                .age(user.classifyAge())
                .mbti(user.getMbti())
                .gender(user.getGender())
                .parent(parentComment)
                .build();
    }
}
