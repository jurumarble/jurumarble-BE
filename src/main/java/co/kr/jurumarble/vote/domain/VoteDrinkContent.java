package co.kr.jurumarble.vote.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "vote_drink_content")
public class VoteDrinkContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vote_id")
    private Long voteId;

    @Column(name = "drink_id_a")
    private Long drinkIdA;

    @Column(name = "drink_id_b")
    private Long drinkIdB;

    public VoteDrinkContent(Long voteId, Long drinkIdA, Long drinkIdB) {
        this.voteId = voteId;
        this.drinkIdA = drinkIdA;
        this.drinkIdB = drinkIdB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteDrinkContent)) return false;
        VoteDrinkContent that = (VoteDrinkContent) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
