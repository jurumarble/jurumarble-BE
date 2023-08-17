package co.kr.jurumarble.statistics.dto.response;

import lombok.Getter;

@Getter
public class TotalStatisticsResponse {

    private Long voteId;
    private Long totalVoteCount;

    public TotalStatisticsResponse(Long voteId, Long totalVoteCount) {
        this.voteId = voteId;
        this.totalVoteCount = totalVoteCount;
    }
}
