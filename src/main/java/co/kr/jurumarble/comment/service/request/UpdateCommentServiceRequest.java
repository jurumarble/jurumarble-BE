package co.kr.jurumarble.comment.service.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdateCommentServiceRequest {
    private String content;

    @Builder
    public UpdateCommentServiceRequest(String content) {
        this.content = content;
    }
}
