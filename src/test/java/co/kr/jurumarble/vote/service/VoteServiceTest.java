package co.kr.jurumarble.vote.service;

import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.domain.VoteGenerator;
import co.kr.jurumarble.vote.repository.BookmarkRepository;
import co.kr.jurumarble.vote.repository.VoteRepository;
import co.kr.jurumarble.vote.repository.VoteResultRepository;
import co.kr.jurumarble.vote.service.request.CreateNormalVoteServiceRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @InjectMocks
    private VoteService voteService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private VoteRepository voteRepository;
    @Mock
    private VoteResultRepository voteResultRepository;
    @Mock
    private BookmarkRepository bookmarkRepository;
    @Mock
    private VoteGenerator voteGenerator;

    @DisplayName("투표를 생성한다.")
    @Test
    void createVote() {
        // given
        Long userId = 1L;

        User user = User.builder()
                .build();

        CreateNormalVoteServiceRequest request = CreateNormalVoteServiceRequest.builder()
                .title("투표 제목")
                .titleA("A 타이틀")
                .titleB("B 타이틀")
                .imageA("A 이미지")
                .imageB("B 이미지")
                .build();
        VoteContent voteContent = request.toVoteContent();

        Vote vote = request.toVote(userId);


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        voteService.createVote(request, userId);

        // then
        verify(voteGenerator, times(1)).createVote(vote, voteContent);
    }
}