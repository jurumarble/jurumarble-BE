package co.kr.jurumarble.comment.service.request;

import co.kr.jurumarble.comment.enums.SortType;
import lombok.Builder;
import lombok.Getter;

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