package co.kr.jurumarble.vote.dto.request;

import co.kr.jurumarble.user.enums.ChoiceType;
import co.kr.jurumarble.vote.dto.DoVoteInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoVoteRequest {

    private ChoiceType choice;

    public DoVoteInfo toService(Long userId, Long voteId) {
        return DoVoteInfo.builder()
                .userId(userId)
                .voteId(voteId)
                .choice(choice)
                .build();
    }

    public DoVoteRequest(ChoiceType choice) {
        this.choice = choice;
    }
}
