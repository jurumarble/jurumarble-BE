package co.kr.jurumarble.vote.dto.response;

import co.kr.jurumarble.vote.repository.dto.MyVotesCntData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetMyVotesResponse {

    private Long writtenVoteCnt;
    private Long joinedVoteCnt;
    private Long bookmarkedVoteCnt;

    public static GetMyVotesResponse fromVotesCntData(MyVotesCntData voteData) {
        return new GetMyVotesResponse(voteData.getWrittenVoteCnt(), voteData.getJoinedVoteCnt(), voteData.getBookmarkedVoteCnt());
    }
}
