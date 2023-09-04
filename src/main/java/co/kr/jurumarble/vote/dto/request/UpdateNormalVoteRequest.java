package co.kr.jurumarble.vote.dto.request;

import co.kr.jurumarble.vote.service.UpdateNormalVoteServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateNormalVoteRequest {

    @Schema(description = "투표 제목", example = "A, B 중 어떤게 나을까요?")
    @NotBlank(message = "투표 제목은 필수입니다.")
    private String title;

    @Schema(description = "투표 상세글")
    @NotBlank(message = "투표 상세글은 필수입니다.")
    private String detail;

    @Schema(description = "A 항목의 제목")
    @NotBlank(message = "투표 A항목의 제목은 필수입니다.")
    private String titleA;

    @Schema(description = "B 항목의 제목")
    @NotBlank(message = "투표 B항목의 제목은 필수입니다.")
    private String titleB;

    @Builder
    public UpdateNormalVoteRequest(String title, String detail, String titleA, String titleB) {
        this.title = title;
        this.detail = detail;
        this.titleA = titleA;
        this.titleB = titleB;
    }

    public UpdateNormalVoteServiceRequest toServiceRequest(Long voteId, Long userId, UpdateNormalVoteRequest request) {

        return UpdateNormalVoteServiceRequest.builder()
                .voteId(voteId)
                .userId(userId)
                .title(request.getTitle())
                .detail(request.getDetail())
                .titleA(request.getTitleA())
                .titleB(request.getTitleB())
                .build();
    }
}