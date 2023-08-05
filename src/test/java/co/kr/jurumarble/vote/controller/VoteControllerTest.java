package co.kr.jurumarble.vote.controller;

import co.kr.jurumarble.config.WebConfig;
import co.kr.jurumarble.token.domain.JwtTokenProvider;
import co.kr.jurumarble.vote.dto.request.CreateVoteRequest;
import co.kr.jurumarble.vote.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VoteController.class)
@Import(JwtTokenProvider.class)
@TestPropertySource(locations = "classpath:application-test.yml")
class VoteControllerTest {
    private static final int TOKEN_VALID_TIME = 30;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VoteService voteService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @DisplayName("토큰과 같이 요청을 보내서 투표를 생성한다.")
    @Test
    void createVote() throws Exception {
        // given
        CreateVoteRequest request = CreateVoteRequest.builder()
                .title("투표 제목")
                .titleA("A 항목 제목")
                .titleB("B 항목 제목")
                .imageA("A 항목 이미지")
                .imageB("B 항목 이미지")
                .build();

        // 테스트용 사용자 토큰 생성
        Long userId = 1L;
        String testToken = jwtTokenProvider.makeJwtToken(userId,TOKEN_VALID_TIME);

        // when // then
        mockMvc.perform(
                post("/api/votes/")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer " + testToken) // 생성된 토큰을 헤더에 추가
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) // 요청에 대한 로그를 더 자세하게 확인 가능
                .andExpect(status().isCreated());
    }
}