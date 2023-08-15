package co.kr.jurumarble.drink.domain.entity;

import co.kr.jurumarble.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "enjoy_drink")
public class EnjoyDrink extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "drink_id")
    private Long drinkId;

    public EnjoyDrink(Long userId, Long drinkId) {
        this.userId = userId;
        this.drinkId = drinkId;
    }
}
