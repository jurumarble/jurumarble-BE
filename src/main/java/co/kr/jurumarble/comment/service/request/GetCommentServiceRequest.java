package co.kr.jurumarble.comment.service.request;

import co.kr.jurumarble.comment.enums.SortType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
public class GetCommentServiceRequest {
    private SortType sortBy;
    private int page;
    private int size;

    @Builder
    public GetCommentServiceRequest(SortType sortBy, int page, int size) {
        this.sortBy = sortBy;
        this.page = page;
        this.size = size;
    }
}