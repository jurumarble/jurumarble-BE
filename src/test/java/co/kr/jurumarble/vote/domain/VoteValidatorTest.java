package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.exception.user.UserNotAccessRightException;
import co.kr.jurumarble.exception.vote.AlreadyUserDoVoteException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.vote.repository.VoteResultRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteValidatorTest {

    @InjectMocks
    private VoteValidator voteValidator;

    @Mock
    private VoteResultRepository voteResultRepository;

    @DisplayName("내가 만든 투표에 참여하는 경우 UserNotAccessRightException을 throw 한다.")
    @Test
    void validPostedUserWhenParcitipateVote(){
        // given
        Long userId = 1L;
        Vote vote = Vote.builder()
                .postedUserId(userId)
                .build();

        User postedUser = User.builder().id(userId).build();

        // when // then
        assertThrows(UserNotAccessRightException.class,
                () -> {
                    voteValidator.validPostedUserWhenParcitipateVote(vote, postedUser);
                });
    }

    @DisplayName("이미 참여한 투표에 참여하는 경우 UserNotAccessRightException을 throw 한다.")
    @Test
    void validAlreadyParcitipatedVote(){
        // given
        Long userId = 1L;
        Vote vote = Vote.builder()
                .build();

        User user = User.builder().id(userId).build();

        when(voteResultRepository.existsByVoteIdAndVotedUserId(vote.getId(), user.getId())).thenReturn(true);
        // when // then
        assertThrows(AlreadyUserDoVoteException.class, () -> voteValidator.validAlreadyParcitipatedVote(vote, user));
    }

}