package co.kr.jurumarble.vote.dto;

import co.kr.jurumarble.user.enums.ChoiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class GetIsUserVoted {

    private boolean isVoted;

    private ChoiceType userChoice;
}
