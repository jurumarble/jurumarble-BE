package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.common.domain.BaseTimeEntity;
import co.kr.jurumarble.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Bookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "VOTE_ID")
    private Long voteId;

//    public void mappingVote(Vote vote) {
//        this.vote = vote;
//        vote.mappingBookmark(this);
//    }

//    public void mappingUser(User user) {
//        this.user = user;
//        user.mappingBookmark(this);
//    }


}