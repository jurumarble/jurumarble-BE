package co.kr.jurumarble.vote.dto;

import co.kr.jurumarble.user.enums.ChoiceType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoVoteInfo {

    private Long userId;

    private Long voteId;

    private ChoiceType choice;

    @Builder
    public DoVoteInfo(Long userId, Long voteId, ChoiceType choice) {
        this.userId = userId;
        this.voteId = voteId;
        this.choice = choice;
    }
}
