package co.kr.jurumarble.user.controller;


import co.kr.jurumarble.token.domain.JwtTokenProvider;
import co.kr.jurumarble.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UserController.class)
@Import(JwtTokenProvider.class)
@TestPropertySource(locations = "classpath:application-test.yml")
public class UserControllerTest {

    private static final int TOKEN_VALID_TIME = 30;
    private static final int TOKEN_EXPIRED_TIME = 0;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


}
