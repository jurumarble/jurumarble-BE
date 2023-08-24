package co.kr.jurumarble.vote.dto.request;

import co.kr.jurumarble.user.enums.ChoiceType;
import co.kr.jurumarble.vote.dto.DoVoteInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class DoVoteRequest {

    @Schema(description = "선택")
    @NotBlank(message = "선택 사항을 필수 입니다.")
    private ChoiceType choice;

    public DoVoteRequest(ChoiceType choice) {
        this.choice = choice;
    }

    public DoVoteInfo toService(Long userId, Long voteId) {
        return DoVoteInfo.builder()
                .userId(userId)
                .voteId(voteId)
                .choice(choice)
                .build();
    }
}
