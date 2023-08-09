package co.kr.jurumarble.vote.dto.request;

import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.service.request.CreateNormalVoteServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateNormalVoteRequest {

    @Schema(description = "투표 제목", example = "A, B 중 어떤게 나을까요?")
    @NotBlank(message = "투표 제목은 필수입니다.")
    private String title;

    @Schema(description = "A 항목의 제목")
    @NotBlank(message = "투표 A항목의 제목은 필수입니다.")
    private String titleA;

    @Schema(description = "B 항목의 제목")
    @NotBlank(message = "투표 B항목의 제목은 필수입니다.")
    private String titleB;

    @Schema(description = "A 이미지")
    @NotBlank(message = "투표 A 이미지는 필수입니다.")
    private String imageA;

    @Schema(description = "B 이미지")
    @NotBlank(message = "투표 B 이미지는 필수입니다.")
    private String imageB;

    @Schema(description = "투표 타입")
    @NotBlank(message = "투표 타입은 필수입니다.")
    private VoteType voteType;

    @Builder
    public CreateNormalVoteRequest(String title, String titleA, String titleB, String imageA, String imageB) {
        this.title = title;
        this.titleA = titleA;
        this.titleB = titleB;
        this.imageA = imageA;
        this.imageB = imageB;
    }

    public CreateNormalVoteServiceRequest toServiceRequest() {
        return CreateNormalVoteServiceRequest.builder()
                .title(title)
                .titleA(titleA)
                .titleB(titleB)
                .imageA(imageA)
                .imageB(imageB)
                .voteType(voteType)
                .build();
    }
}