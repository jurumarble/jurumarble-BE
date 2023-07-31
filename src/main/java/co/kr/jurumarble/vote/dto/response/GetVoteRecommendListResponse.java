package co.kr.jurumarble.vote.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetVoteRecommendListResponse {

    private List<String> recommendKeywords;

    public GetVoteRecommendListResponse(List<String> recommendKeywords) {
        this.recommendKeywords = recommendKeywords;
    }
}
