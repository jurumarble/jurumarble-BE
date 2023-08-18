package co.kr.jurumarble.comment.dto.request;

import co.kr.jurumarble.comment.service.request.UpdateCommentServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdateCommentRequest {

    @Schema(description = "수정할 댓글 내용", example = "수정된 내용입니다.")
    @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
    private String content;

    @Builder
    public UpdateCommentRequest(String content) {
        this.content = content;
    }

    public UpdateCommentServiceRequest toServiceRequest(){
        return UpdateCommentServiceRequest.builder()
                .content(content)
                .build();
    }
}
