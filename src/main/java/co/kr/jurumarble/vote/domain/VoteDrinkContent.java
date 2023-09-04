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

    @Column(name = "drink_a_image")
    private String drinkAImage;

    @Column(name = "drink_b_image")
    private String drinkBImage;

    private String region;

    @Builder
    public VoteDrinkContent(Long id, Long voteId, Long drinkAId, Long drinkBId, String drinkAName, String drinkAType, String drinkBName, String drinkBType, String drinkAImage, String drinkBImage, String region) {
        validateDrinksDuplicated(drinkAId, drinkBId);
        this.id = id;
        this.voteId = voteId;
        this.drinkAId = drinkAId;
        this.drinkBId = drinkBId;
        this.drinkAName = drinkAName;
        this.drinkAType = drinkAType;
        this.drinkBName = drinkBName;
        this.drinkBType = drinkBType;
        this.drinkAImage = drinkAImage;
        this.drinkBImage = drinkBImage;
        this.region = region;
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
                .drinkAImage(drinkA.getImage())
                .drinkBImage(drinkB.getImage())
                .region(drinkA.getRegion())
                .build();
    }

    private void validateDrinksDuplicated(Long drinkAId, Long drinkBId) {
        if (Objects.equals(drinkAId, drinkBId)) {
            throw new VoteDrinksDuplicatedException();
        }
    }

    public void updateFromDrinks(DrinksUsedForVote drinksUsedForVote) {
        Drink drinkA = drinksUsedForVote.getDrinkA();
        Drink drinkB = drinksUsedForVote.getDrinkB();
        this.drinkAId = drinkA.getId();
        this.drinkBId = drinkB.getId();
        this.drinkAImage = drinkA.getImage();
        this.drinkBImage = drinkB.getImage();
        this.drinkAName = drinkA.getName();
        this.drinkBName = drinkB.getName();
        this.drinkAType = drinkA.getType();
        this.drinkBType = drinkB.getType();
        this.region = drinkA.getRegion();
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
