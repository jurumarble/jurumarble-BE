package co.kr.jurumarble.comment.service.request;

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
}
