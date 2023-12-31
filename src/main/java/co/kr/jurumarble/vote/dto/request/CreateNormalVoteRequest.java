package co.kr.jurumarble.vote.dto.request;

import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.service.request.CreateNormalVoteServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateNormalVoteRequest {

    @Schema(description = "투표 제목", example = "A, B 중 어떤게 나을까요?")
    @NotBlank(message = "투표 제목은 필수입니다.")
    @Size(max = 75, message = "제목은 최대 75자까지 입력 가능합니다.")
    private String title;

    @Schema(description = "투표 상세", example = "A는 ~때문에 고민이고 B는 ~때문에 고민입니다")
    @Size(max = 600, message = "내용은 최대 600자까지 입력 가능합니다.")
    private String detail;

    @Schema(description = "A 항목의 제목")
    @NotBlank(message = "투표 A항목의 제목은 필수입니다.")
    @Size(max = 55, message = "제목은 최대 55자까지 입력 가능합니다.")
    private String titleA;

    @Schema(description = "B 항목의 제목")
    @NotBlank(message = "투표 B항목의 제목은 필수입니다.")
    @Size(max = 55, message = "제목은 최대 55자까지 입력 가능합니다.")
    private String titleB;

    @Schema(description = "A 이미지", example = "https://shopping-phinf.pstatic.net/main_8259853/82598534476.2.jpg")
    private String imageA;

    @Schema(description = "B 이미지", example = "https://shopping-phinf.pstatic.net/main_8204301/82043013300.5.jpg")
    private String imageB;

    @Builder
    public CreateNormalVoteRequest(String title, String detail, String titleA, String titleB, String imageA, String imageB) {
        this.title = title;
        this.detail = detail;
        this.titleA = titleA;
        this.titleB = titleB;
        this.imageA = imageA;
        this.imageB = imageB;
    }

    public CreateNormalVoteServiceRequest toServiceRequest() {
        return CreateNormalVoteServiceRequest.builder()
                .title(title)
                .detail(detail)
                .titleA(titleA)
                .titleB(titleB)
                .imageA(imageA)
                .imageB(imageB)
                .voteType(VoteType.NORMAL)
                .build();
    }
}