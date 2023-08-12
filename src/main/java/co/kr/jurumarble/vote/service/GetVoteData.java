package co.kr.jurumarble.vote.service;

import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.dto.response.GetVoteUserResponse;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetVoteData {

    private final GetVoteUserResponse writer;

    private final LocalDateTime voteCreatedDate;

    private final String title;

    private final String imageA;

    private final String imageB;

    private final String titleA;

    private final String titleB;

    private final String description;

    public GetVoteData(Vote vote, User user, VoteContent voteContent) {

        this.writer = getGetVoteUserResponse(user);
        this.voteCreatedDate = vote.getCreatedDate();
        this.title = vote.getTitle();
        this.imageA = voteContent.getImageA();
        this.imageB = voteContent.getImageB();
        this.titleA = voteContent.getTitleA();
        this.titleB = voteContent.getTitleB();
        this.description = vote.getDetail();

    }

    private GetVoteUserResponse getGetVoteUserResponse(User user) {
        return GetVoteUserResponse.builder()
                .userImage(user.getImageUrl())
                .userGender(user.getGender())
                .userAge(user.classifyAge())
                .userMbti(user.getMbti())
                .nickName(user.getNickname())
                .build();
    }
}
