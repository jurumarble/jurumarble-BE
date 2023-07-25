package co.kr.jurumarble.vote.domain;


import co.kr.jurumarble.common.domain.BaseTimeEntity;
import co.kr.jurumarble.enums.AgeType;
import co.kr.jurumarble.enums.GenderType;
import co.kr.jurumarble.enums.MBTIType;
import co.kr.jurumarble.user.domain.UserEntity;
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
    private String imageA;

    @Column
    private String imageB;

    @Column
    private String titleA;

    @Column
    private String titleB;

    @Column
    private String detail;

    @Column
    @Enumerated(EnumType.STRING)
    private GenderType filteredGender;

    /**
     * 필터링 거는 나이는 10대, 20대, 30대 이므로 여기는 AGE enum을 이용하고
     * User 엔티티의 나이는 실제 나이를 입력받으므로 INTEGER 로 저장
     */
    @Column
    @Enumerated(EnumType.STRING)
    private AgeType filteredAge;

    @Column
    @Enumerated(EnumType.STRING)
    private MBTIType filteredMbti;

    public VoteEntity(UserEntity postedUser, List<VoteResultEntity> voteResultList, String title, String imageA, String imageB, String titleA, String titleB, String detail, GenderType filteredGender, AgeType filteredAge, MBTIType filteredMbti) {
        this.postedUser = postedUser;
        this.voteResultList = voteResultList;
        this.title = title;
        this.imageA = imageA;
        this.imageB = imageB;
        this.titleA = titleA;
        this.titleB = titleB;
        this.detail = detail;
        this.filteredGender = filteredGender;
        this.filteredAge = filteredAge;
        this.filteredMbti = filteredMbti;
    }

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vote", cascade = CascadeType.REMOVE)
//    private List<Bookmark> bookmarkList = new ArrayList<>();

//    public void removeBookmark(Bookmark bookmark) {
//        this.bookmarkList.remove(bookmark);
//    }

//    public VoteEntity(CreateVoteRequest request, UserEntity user) {
//        this.postedUser = user;
//        this.title = request.getTitle();
//        this.imageA = request.getImageA();
//        this.imageB = request.getImageB();
//        this.titleA = request.getTitleA();
//        this.titleB = request.getTitleB();
//        this.filteredGender = request.getFilteredGender();
//        this.filteredAge = request.getFilteredAge();
//        this.filteredMbti = request.getFilteredMbti();
//    }

    //
//    public void  update(UpdateVoteRequest updateVoteRequest) {
//        this.title = updateVoteRequest.getTitle();
//        this.titleA = updateVoteRequest.getTitleA();
//        this.titleB = updateVoteRequest.getTitleB();
//        this.detail = updateVoteRequest.getDetail();
//        this.category = updateVoteRequest.getCategory();
//    }
//
    public void addVoteResult(VoteResultEntity voteResult) {
        this.voteResultList.add(voteResult);
    }
//
//    public void mappingBookmark(Bookmark bookmark) {
//        this.bookmarkList.add(bookmark);
//    }


}