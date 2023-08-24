package co.kr.jurumarble.vote.controller;

import co.kr.jurumarble.token.domain.JwtTokenProvider;
import co.kr.jurumarble.vote.dto.request.CreateNormalVoteRequest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VoteController.class)
@Import(JwtTokenProvider.class)
@TestPropertySource(locations = "classpath:application-test.yml")
class VoteControllerTest {
    private static final int TOKEN_VALID_TIME = 30;
    private static final int TOKEN_EXPIRED_TIME = 0;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VoteService voteService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @DisplayName("토큰과 같이 요청을 보내서 일반 투표를 생성한다.")
    @Test
    void createVote() throws Exception {
        // given
        CreateNormalVoteRequest request = CreateNormalVoteRequest.builder()
                .title("투표 제목")
                .titleA("A 항목 제목")
                .titleB("B 항목 제목")
                .imageA("A 항목 이미지")
                .imageB("B 항목 이미지")
                .build();

        // 테스트용 사용자 토큰 생성
        Long userId = 1L;
        String testToken = jwtTokenProvider.makeJwtToken(userId, TOKEN_VALID_TIME);

        // when // then
        mockMvc.perform(
                        post("/api/votes/normal")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + testToken) // 생성된 토큰을 헤더에 추가
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) // 요청에 대한 로그를 더 자세하게 확인 가능
                .andExpect(status().isCreated());
    }

    @DisplayName("토큰 없이 일반 투표 생성 요청을 보내서 401 에러를 반환한다.")
    @Test
    void createVoteWithOutToken() throws Exception {
        // given
        CreateNormalVoteRequest request = CreateNormalVoteRequest.builder()
                .title("투표 제목")
                .titleA("A 항목 제목")
                .titleB("B 항목 제목")
                .imageA("A 항목 이미지")
                .imageB("B 항목 이미지")
                .build();


        // when // then
        mockMvc.perform(
                        post("/api/votes/normal")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) // 요청에 대한 로그를 더 자세하게 확인 가능
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value("TOKEN_EXPIRED"));
    }

    @DisplayName("만료된 토큰과 일반 투표 생성 요청을 보내서 401 에러를 반환한다.")
    @Test
    void createVoteWithExpiredToken() throws Exception {
        // given
        CreateNormalVoteRequest request = CreateNormalVoteRequest.builder()
                .title("투표 제목")
                .titleA("A 항목 제목")
                .titleB("B 항목 제목")
                .imageA("A 항목 이미지")
                .imageB("B 항목 이미지")
                .build();

        // 테스트용 사용자 토큰 생성
        Long userId = 1L;
        String testToken = jwtTokenProvider.makeJwtToken(userId, TOKEN_EXPIRED_TIME);


        // when // then
        mockMvc.perform(
                        post("/api/votes/normal")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + testToken) // 생성된 토큰을 헤더에 추가
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) // 요청에 대한 로그를 더 자세하게 확인 가능
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value("TOKEN_EXPIRED"));
    }
}