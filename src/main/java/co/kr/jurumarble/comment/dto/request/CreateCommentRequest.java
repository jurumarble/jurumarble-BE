package co.kr.jurumarble.comment.dto.request;

import co.kr.jurumarble.comment.service.request.CreateCommentServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateCommentRequest {

    @Schema(description = "부모 댓글 ID")
    private Long parentId;

    @Schema(description = "댓글 내용", example = "좋습니다!")
    @NotBlank
    @Size(max = 600, message = "댓글은 최대 600자까지 입력 가능합니다.")
    private String content;

    @Builder
    public CreateCommentRequest(Long parentId, String content) {
        this.parentId = parentId;
        this.content = content;
    }

    public CreateCommentServiceRequest toServiceRequest() {
        return CreateCommentServiceRequest.builder()
                .parentId(parentId)
                .content(content)
                .build();
    }
}
