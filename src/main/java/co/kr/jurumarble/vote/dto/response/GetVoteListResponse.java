package co.kr.jurumarble.vote.dto.response;

import co.kr.jurumarble.vote.dto.NormalVoteData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

@Getter
@NoArgsConstructor
public class GetVoteListResponse {

    private Slice<NormalVoteData> voteSlice;

    public GetVoteListResponse(Slice<NormalVoteData> voteSlice) {
        this.voteSlice = voteSlice;
    }
}
