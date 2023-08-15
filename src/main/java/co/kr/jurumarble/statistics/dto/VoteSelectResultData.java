package co.kr.jurumarble.statistics.dto;

import lombok.Getter;

@Getter
public class VoteSelectResultData {

    private Long totalCountA;

    private Long totalCountB;

    private int percentA;

    private int percentB;

    public VoteSelectResultData(Long totalCountA, Long totalCountB, int percentA, int percentB) {
        this.totalCountA = totalCountA;
        this.totalCountB = totalCountB;
        this.percentA = percentA;
        this.percentB = percentB;
    }
}