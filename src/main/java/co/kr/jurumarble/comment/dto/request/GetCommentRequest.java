package co.kr.jurumarble.comment.dto.request;

import co.kr.jurumarble.comment.enums.SortType;
import co.kr.jurumarble.comment.service.request.GetCommentServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class GetCommentRequest {

    @Schema(description = "정렬 방식", defaultValue = "ByTime")
    @NotNull(message = "정렬 방식은 필수입니다.")
    private SortType sortBy;

    @Schema(description = "페이지 번호", defaultValue = "0")
    @NotNull(message = "페이지 번호는 필수입니다.")
    private int page;

    @Schema(description = "페이지 크기", defaultValue = "5")
    @NotNull(message = "페이지 크기는 필수입니다.")
    private int size;

    @Builder
    public GetCommentRequest(SortType sortBy, int page, int size) {
        this.sortBy = sortBy;
        this.page = page;
        this.size = size;
    }

    public GetCommentServiceRequest toServiceRequest(){
        return GetCommentServiceRequest.builder()
                .sortBy(sortBy)
                .page(page)
                .size(size)
                .build();
    }
}