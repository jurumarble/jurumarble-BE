package co.kr.jurumarble.bookmark.domain;

import co.kr.jurumarble.common.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bookmark")
public class Bookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "VOTE_ID")
    private Long voteId;

    public Bookmark(Long userId, Long voteId) {
        this.userId = userId;
        this.voteId = voteId;
    }

}