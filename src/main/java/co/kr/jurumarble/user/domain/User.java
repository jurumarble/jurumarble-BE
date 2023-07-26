package co.kr.jurumarble.user.domain;

import co.kr.jurumarble.common.domain.BaseTimeEntity;
import co.kr.jurumarble.user.dto.AddUserInfo;
import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.user.enums.ProviderType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @Column
    private String nickname;

    @Column
    private String email;

    private String password;

    private String imageUrl;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;    // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지

    private String providerId;  // oauth2를 이용할 경우 아이디값

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Enumerated(EnumType.STRING)
    private MbtiType mbti;

    @Column
    private LocalDateTime modifiedMbtiDate;


    public AgeType classifyAge(Integer age) {
        AgeType ageGroup;
        switch (age / 10) {
            case 1:
                ageGroup = AgeType.teenager;
                break;
            case 2:
                ageGroup = AgeType.twenties;
                break;
            case 3:
                ageGroup = AgeType.thirties;
                break;
            case 4:
                ageGroup = AgeType.fourties;
                break;
            case 5:
                ageGroup = AgeType.fifties;
                break;
            default:
                ageGroup = AgeType.NULL;
                break;
        }
        return ageGroup;
    }

    public void addInfo(AddUserInfo addUserInfo) {
        this.mbti = addUserInfo.getMbti();
        this.age = addUserInfo.getAge();
        this.gender = addUserInfo.getGender();
    }

    @Builder
    private User(Long id, String nickname, String email, String imageUrl, String password, ProviderType providerType, String providerId, Integer age, GenderType gender, MbtiType mbti, LocalDateTime modifiedMbtiDate) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
        this.password = password;
        this.providerType = providerType;
        this.providerId = providerId;
        this.age = age;
        this.gender = gender;
        this.mbti = mbti;
        this.modifiedMbtiDate = modifiedMbtiDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}