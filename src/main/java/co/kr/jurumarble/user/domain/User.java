package co.kr.jurumarble.user.domain;

import co.kr.jurumarble.common.domain.BaseTimeEntity;
import co.kr.jurumarble.exception.user.AlreadyDeletedUserException;
import co.kr.jurumarble.user.dto.AddUserInfo;
import co.kr.jurumarble.user.dto.UpdateUserInfo;
import co.kr.jurumarble.user.enums.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Where(clause = "deleted_date IS NULL")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String email;

    private String password;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "year_of_birth")
    private Integer yearOfBirth;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Enumerated(EnumType.STRING)
    private MbtiType mbti;

    @Enumerated(EnumType.STRING)
    @Column(name = "alcohol_limit")
    private AlcoholLimitType alcoholLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type")
    private ProviderType providerType;    // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지

    @Column(name = "provider_id")
    private String providerId;  // oauth2를 이용할 경우 아이디값

    @Column(name = "modified_mbti_date")
    private LocalDateTime modifiedMbtiDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;


    @Builder
    private User(Long id, String nickname, String email, String imageUrl, String password, ProviderType providerType, String providerId, Integer yearOfBirth, GenderType gender, MbtiType mbti, LocalDateTime modifiedMbtiDate) {
        validIsUserDeleted();
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
        this.password = password;
        this.providerType = providerType;
        this.providerId = providerId;
        this.yearOfBirth = yearOfBirth;
        this.gender = gender;
        this.mbti = mbti;
        this.modifiedMbtiDate = modifiedMbtiDate;
    }

    public AgeType classifyAge() {
        if (age == null) {
            return null; // 혹은 원하는 다른 동작 수행
        }
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
                ageGroup = null;
                break;
        }
        return ageGroup;
    }

    public void addInfo(AddUserInfo addUserInfo) {
        this.mbti = addUserInfo.getMbti();
        this.yearOfBirth = addUserInfo.getBirthOfAge();
        this.gender = addUserInfo.getGender();
        this.alcoholLimit = addUserInfo.getAlcoholLimit();
        this.modifiedMbtiDate = LocalDateTime.now();
    }

//    public void mappingBookmark(Bookmark bookmark) {
//        this.bookmarkList.add(bookmark);
//    }

    private void validIsUserDeleted() {
        if (!(deletedDate == null)) {
            throw new AlreadyDeletedUserException();
        }
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

    public void deleteUser() {
        this.deletedDate = LocalDateTime.now();
    }

    public void updateUser(UpdateUserInfo info) {
        this.nickname = info.getNickname();
        this.imageUrl = info.getImageUrl();
        this.alcoholLimit = info.getAlcoholLimit();
    }

    public void updateMbti(MbtiType mbti) {
        this.mbti = mbti;
        this.modifiedMbtiDate = LocalDateTime.now();
    }
}