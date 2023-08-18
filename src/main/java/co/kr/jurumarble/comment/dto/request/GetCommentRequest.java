package co.kr.jurumarble.comment.dto.request;

import co.kr.jurumarble.comment.enums.SortType;
import io.swagger.v3.oas.annotations.media.Schema;
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
}