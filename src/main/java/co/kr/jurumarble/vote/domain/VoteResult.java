package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.common.domain.BaseTimeEntity;
import co.kr.jurumarble.user.enums.ChoiceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "vote_result")
public class VoteResult extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "vote_id")
    private Long voteId;

    @Column(name = "voted_user_id")
    private Long votedUserId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChoiceType choice;

    public VoteResult(Long voteId, Long votedUserId, ChoiceType choice) {
        this.voteId = voteId;
        this.votedUserId = votedUserId;
        this.choice = choice;
    }
}