package co.kr.jurumarble.vote.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateVoteRequest {

    @Schema(description = "투표 제목", example = "A, B 중 어떤게 나을까요?")
    @NotBlank
    private String title;

    @Schema(description = "투표 상세글")
    @NotBlank
    private String detail;

    @Schema(description = "A 항목의 제목")
    @NotBlank
    private String titleA;

    @Schema(description = "B 항목의 제목")
    @NotBlank
    private String titleB;

    @Builder
    public UpdateVoteRequest(String title, String detail, String titleA, String titleB) {
        this.title = title;
        this.detail = detail;
        this.titleA = titleA;
        this.titleB = titleB;
    }
}