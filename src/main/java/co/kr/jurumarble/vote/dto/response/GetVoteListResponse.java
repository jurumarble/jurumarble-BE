package co.kr.jurumarble.vote.dto.response;

import co.kr.jurumarble.vote.dto.VoteListData;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

@Getter
@NoArgsConstructor
public class GetVoteListResponse {

    private Slice<VoteListData> voteSlice;

    public GetVoteListResponse(Slice<VoteListData> voteSlice) {
        this.voteSlice = voteSlice;
    }
}
