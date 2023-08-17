package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.exception.user.UserNotAccessRightException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.vote.repository.VoteResultRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VoteValidatorTest {

    @InjectMocks
    private VoteValidator voteValidator;

    @Mock
    private VoteResultRepository voteResultRepository;

    @DisplayName("내가 만든 투표에 참여하는 경우 UserNotAccessRightException 을 throw 한다.")
    @Test
    void validPostedUserWhenParcitipateVote(){
        // given
        Long userId = 1L;
        Vote vote = Vote.builder()
                .postedUserId(userId)
                .build();

        User postedUser = User.builder().id(userId).build();

        // when // then
        Assertions.assertThrows(UserNotAccessRightException.class,
                () -> {
                    voteValidator.validPostedUserWhenParcitipateVote(vote, postedUser);
                });
    }

}