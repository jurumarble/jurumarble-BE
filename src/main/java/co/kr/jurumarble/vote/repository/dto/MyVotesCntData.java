package co.kr.jurumarble.vote.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyVotesCntData {

    private Long writtenVoteCnt;
    private Long joinedVoteCnt;
    private Long bookmarkedVoteCnt;
}
