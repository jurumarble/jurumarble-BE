package co.kr.jurumarble.vote.domain;


import co.kr.jurumarble.drink.domain.dto.DrinksUsedForVote;
import co.kr.jurumarble.drink.domain.entity.Drink;
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

    @Column(name = "drink_a_id")
    private Long drinkAId;

    @Column(name = "drink_b_id")
    private Long drinkBId;

    @Column(name = "drink_a_name")
    private String drinkAName;

    @Column(name = "drink_a_type")
    private String drinkAType;

    @Column(name = "drink_b_name")
    private String drinkBName;

    @Column(name = "drink_b_type")
    private String drinkBType;

    @Builder
    public VoteDrinkContent(Long voteId, Long drinkAId, Long drinkBId, String drinkAName, String drinkAType, String drinkBName, String drinkBType) {
        validateDrinksDuplicated(drinkAId, drinkBId);
        this.voteId = voteId;
        this.drinkAId = drinkAId;
        this.drinkBId = drinkBId;
        this.drinkAName = drinkAName;
        this.drinkAType = drinkAType;
        this.drinkBName = drinkBName;
        this.drinkBType = drinkBType;
    }

    public static VoteDrinkContent createFromDrinks(DrinksUsedForVote drinksUsedForVote) {
        Drink drinkA = drinksUsedForVote.getDrinkA();
        Drink drinkB = drinksUsedForVote.getDrinkB();
        return VoteDrinkContent.builder()
                .drinkAId(drinkA.getId())
                .drinkBId(drinkB.getId())
                .drinkAName(drinkA.getName())
                .drinkBName(drinkB.getName())
                .drinkAType(drinkA.getType())
                .drinkBType(drinkB.getType())
                .build();
    }

    private void validateDrinksDuplicated(Long drinkAId, Long drinkBId) {
        if (Objects.equals(drinkAId, drinkBId)) {
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
