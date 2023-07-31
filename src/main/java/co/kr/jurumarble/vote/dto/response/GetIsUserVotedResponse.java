package co.kr.jurumarble.vote.dto.response;

import co.kr.jurumarble.user.enums.ChoiceType;
import co.kr.jurumarble.vote.dto.GetIsUserVoted;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GetIsUserVotedResponse {

    private boolean isVoted;

    private ChoiceType userChoice;

    public void converter(GetIsUserVoted getIsUserVoted) {
        isVoted = getIsUserVoted.isVoted();
        userChoice = getIsUserVoted.getUserChoice();
    }
}
