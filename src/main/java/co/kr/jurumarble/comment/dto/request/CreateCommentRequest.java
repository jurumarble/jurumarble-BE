package co.kr.jurumarble.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class CreateCommentRequest {

    @Schema(description = "부모 댓글 ID")
    private Long parentId;

    @Schema(description = "댓글 내용", example = "좋습니다!", maxLength = 500)
    @NotBlank
    private String content;
}
