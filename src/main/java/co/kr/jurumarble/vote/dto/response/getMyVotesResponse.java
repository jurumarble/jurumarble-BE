package co.kr.jurumarble.vote.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class getMyVotesResponse {

    private Integer writtenVoteCnt;
    private Integer joinedVoteCnt;
    private Integer bookmarkedVoteCnt;
}
