package co.kr.jurumarble.comment.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateCommentServiceRequest {
    private String content;

    @Builder
    public UpdateCommentServiceRequest(String content) {
        this.content = content;
    }
}
