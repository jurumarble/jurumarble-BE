package co.kr.jurumarble.vote.domain;


import co.kr.jurumarble.common.domain.BaseTimeEntity;
import co.kr.jurumarble.enums.AgeType;
import co.kr.jurumarble.enums.GenderType;
import co.kr.jurumarble.enums.MBTIType;
import co.kr.jurumarble.user.domain.UserEntity;
import co.kr.jurumarble.vote.dto.request.CreateVoteRequest;
import co.kr.jurumarble.vote.dto.request.UpdateVoteRequest;
import co.kr.jurumarble.vote.dto.response.GetVoteResponse;
import co.kr.jurumarble.vote.dto.response.GetVoteUserResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class VoteEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "VOTE_ID")
    private Long id;

    /**
     * User 와의 연관관계 주인
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserEntity postedUser;


    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "vote", fetch = FetchType.LAZY)
    private List<VoteResultEntity> voteResultList = new ArrayList<>();

    @Column
    private String title;

    @Column
    private String detail;

    @Column
    @Enumerated(EnumType.STRING)
    private GenderType filteredGender;

    @Column
    @Enumerated(EnumType.STRING)
    private AgeType filteredAge;

    @Column
    @Enumerated(EnumType.STRING)
    private MBTIType filteredMbti;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "VOTE_CONTENT_ID")
    private VoteContentEntity voteContent;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vote", cascade = CascadeType.REMOVE)
//    private List<Bookmark> bookmarkList = new ArrayList<>();

//    public void removeBookmark(Bookmark bookmark) {
//        this.bookmarkList.remove(bookmark);
//    }

    public VoteEntity(CreateVoteRequest request, UserEntity user, VoteContentEntity voteContent) {
        this.postedUser = user;
        this.title = request.getTitle();
        this.voteContent = voteContent;
        this.filteredGender = request.getFilteredGender();
        this.filteredAge = request.getFilteredAge();
        this.filteredMbti = request.getFilteredMbti();
    }

    public void addVoteResult(VoteResultEntity voteResult) {
        this.voteResultList.add(voteResult);
    }

    public GetVoteResponse toDto() {

        GetVoteUserResponse getVoteUserResponse = GetVoteUserResponse.builder()
                .userImage(postedUser.getImageUrl())
                .userGender(postedUser.getGender())
                .userAge(postedUser.classifyAge(postedUser.getAge()))
                .userMbti(postedUser.getMbti())
                .nickName(postedUser.getNickname())
                .build();

        return GetVoteResponse.builder()
                .writer(getVoteUserResponse)
                .voteCreatedDate(getCreatedDate())
                .title(title)
                .imageA(voteContent.getImageA())
                .imageB(voteContent.getImageB())
                .filteredGender(filteredGender)
                .filteredAge(filteredAge)
                .filteredMbti(filteredMbti)
                .titleA(voteContent.getTitleA())
                .titleB(voteContent.getTitleB())
                .description(detail)
                .build();

    }

    public boolean isUsersVote(Long userId) {

        return this.postedUser.getId().equals(userId);

    }

    public void update(UpdateVoteRequest request) {
        this.title = request.getTitle();
        this.detail = request.getDetail();
        this.getVoteContent().update(request.getTitleA(), request.getTitleB());
    }
//
//    public void mappingBookmark(Bookmark bookmark) {
//        this.bookmarkList.add(bookmark);
//    }


}