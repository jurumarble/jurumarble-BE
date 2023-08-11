package co.kr.jurumarble.vote.service;

import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.dto.response.GetVoteUserResponse;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetVoteData {

    private GetVoteUserResponse writer;

    private LocalDateTime voteCreatedDate;

    private String title;

    private String imageA;

    private String imageB;

    private String titleA;

    private String titleB;

    private String description;

    public GetVoteData(Vote vote, User user, VoteContent voteContent) {

        GetVoteUserResponse getVoteUserResponse = GetVoteUserResponse.builder()
                .userImage(user.getImageUrl())
                .userGender(user.getGender())
                .userAge(user.classifyAge(user.getAge()))
                .userMbti(user.getMbti())
                .nickName(user.getNickname())
                .build();

        this.writer = getVoteUserResponse;
        this.voteCreatedDate = vote.getCreatedDate();
        this.title = vote.getTitle();
        this.imageA = voteContent.getImageA();
        this.imageB = voteContent.getImageB();
        this.titleA = voteContent.getTitleA();
        this.titleB = voteContent.getTitleB();
        this.description = vote.getDetail();

    }
}
