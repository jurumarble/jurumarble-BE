package co.kr.jurumarble.vote.service;

import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class VoteServiceTest {

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoteRepository voteRepository;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .nickname("nickname")
                .age(20)
                .email("example@naver.com")
                .build();
        userRepository.save(user);
    }

    @DisplayName("투표를 생성한다.")
    @Test
    void createVote(){
        // given
        User user = userRepository.findByNickname("nickname").orElseThrow(UserNotFoundException::new);

        CreateVoteServiceRequest request = CreateVoteServiceRequest.builder()
                .title("투표 제목")
                .titleA("A 타이틀")
                .titleB("B 타이틀")
                .imageA("A 이미지")
                .imageB("B 이미지")
                .build();

        // when
        voteService.createVote(request, user.getId());

        // then


    }

}