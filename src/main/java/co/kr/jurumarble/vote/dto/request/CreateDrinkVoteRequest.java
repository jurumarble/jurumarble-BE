package co.kr.jurumarble.vote.dto.request;

import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.service.request.CreateDrinkVoteServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateDrinkVoteRequest {

    @Schema(description = "투표 제목", example = "A, B 중 어떤게 나을까요?")
    @NotBlank(message = "투표 제목은 필수입니다.")
    private String title;
    @Schema(description = "전통주 후보 A의 id")
    private Long drinkIdA;

    @Schema(description = "전통주 후보 B의 id")
    private Long drinkIdB;

    @Schema(description = "투표 타입")
    private VoteType voteType;

    public CreateDrinkVoteServiceRequest toServiceRequest() {
        return CreateDrinkVoteServiceRequest.builder()
                .title(title)
                .drinkIdA(drinkIdA)
                .drinkIdB(drinkIdB)
                .voteType(voteType)
                .build();
    }
}
