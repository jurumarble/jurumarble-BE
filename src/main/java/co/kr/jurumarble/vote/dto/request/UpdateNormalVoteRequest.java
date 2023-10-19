package co.kr.jurumarble.vote.dto.request;

import co.kr.jurumarble.vote.service.request.UpdateNormalVoteServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateNormalVoteRequest {

    @Schema(description = "투표 제목", example = "A, B 중 어떤게 나을까요?")
    @NotBlank(message = "투표 제목은 필수입니다.")
    @Size(max = 75, message = "제목은 최대 75자까지 입력 가능합니다.")
    private String title;

    @Schema(description = "투표 상세글")
    @NotBlank(message = "투표 상세글은 필수입니다.")
    @Size(max = 600, message = "내용은 최대 600자까지 입력할 가능합니다.")
    private String detail;

    @Schema(description = "A 항목의 제목")
    @NotBlank(message = "투표 A항목의 제목은 필수입니다.")
    @Size(max = 55, message = "제목은 최대 55자까지 입력 가능합니다.")
    private String titleA;

    @Schema(description = "B 항목의 제목")
    @NotBlank(message = "투표 B항목의 제목은 필수입니다.")
    @Size(max = 55, message = "제목은 최대 55자까지 입력 가능합니다.")
    private String titleB;

    @Builder
    public UpdateNormalVoteRequest(String title, String detail, String titleA, String titleB) {
        this.title = title;
        this.detail = detail;
        this.titleA = titleA;
        this.titleB = titleB;
    }

    public UpdateNormalVoteServiceRequest toServiceRequest(Long voteId, Long userId) {

        return UpdateNormalVoteServiceRequest.builder()
                .voteId(voteId)
                .userId(userId)
                .title(title)
                .detail(detail)
                .titleA(titleA)
                .titleB(titleB)
                .build();
    }
}