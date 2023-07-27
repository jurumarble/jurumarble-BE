package co.kr.jurumarble.vote.dto;

import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.vote.domain.Vote;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VoteListData {

    private final Long voteId;

    private final UserData writer;

    private final String title;

    private final GenderType filteredGender;

    private final AgeType filteredAge;

    private final MbtiType filteredMbti;

    private final String imageA;

    private final String imageB;

    private final String titleA;

    private final String titleB;

    private final String detail;

    private final LocalDateTime modifiedDate;

    private final Long countVoted;

    public VoteListData(final Vote vote, final Long countVoted) {
        this.voteId = vote.getId();
        this.title = vote.getTitle();
        this.filteredGender = vote.getFilteredGender();
        this.filteredAge = vote.getFilteredAge();
        this.filteredMbti = vote.getFilteredMbti();
        this.imageA = vote.getVoteContent().getImageA();
        this.imageB = vote.getVoteContent().getImageB();
        this.modifiedDate = vote.getModifiedDate();
        this.titleA = vote.getVoteContent().getTitleA();
        this.titleB = vote.getVoteContent().getTitleB();
        this.detail = vote.getDetail();
        this.countVoted = countVoted;
        UserData userData = new UserData(vote.getPostedUser());
        this.writer = userData;
    }
}
