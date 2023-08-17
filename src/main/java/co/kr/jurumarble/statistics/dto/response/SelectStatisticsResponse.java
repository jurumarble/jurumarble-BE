package co.kr.jurumarble.statistics.dto.response;

import co.kr.jurumarble.statistics.dto.VoteSelectResultData;
import lombok.Getter;

@Getter
public class SelectStatisticsResponse {

    private Long voteId;
    private Long totalCountA;
    private Long totalCountB;
    private int percentageA;
    private int percentageB;


    public SelectStatisticsResponse(Long voteId, VoteSelectResultData voteSelectResultData) {
        this.voteId = voteId;
        this.totalCountA = voteSelectResultData.getTotalCountA();
        this.totalCountB = voteSelectResultData.getTotalCountB();
        this.percentageA = voteSelectResultData.getPercentA();
        this.percentageB = voteSelectResultData.getPercentB();
    }
}
