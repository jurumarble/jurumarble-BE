package co.kr.jurumarble.vote.dto.response;

import co.kr.jurumarble.vote.dto.VoteData;
import co.kr.jurumarble.vote.dto.VoteListData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

@Getter
@NoArgsConstructor
public class GetVoteListResponse {

    private Slice<VoteData> voteSlice;

    public GetVoteListResponse(Slice<VoteData> voteSlice) {
        this.voteSlice = voteSlice;
    }
}
