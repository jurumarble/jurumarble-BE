package co.kr.jurumarble.comment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdateCommentRequest {
    @NotBlank
    private String content;
}
