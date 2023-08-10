package co.kr.jurumarble.vote.domain;


import co.kr.jurumarble.exception.vote.VoteDrinksDuplicatedException;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder
    public VoteDrinkContent(Long voteId, Long drinkIdA, Long drinkIdB) {
        validateDrinksDuplicated(drinkIdA, drinkIdB);
        this.voteId = voteId;
        this.drinkIdA = drinkIdA;
        this.drinkIdB = drinkIdB;
    }

    private void validateDrinksDuplicated(Long drinkIdA, Long drinkIdB) {
        if (Objects.equals(drinkIdA, drinkIdB)) {
            throw new VoteDrinksDuplicatedException();
        }
    }

    public void mappingVote(Long voteId) {
        this.voteId = voteId;
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