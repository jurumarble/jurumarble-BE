package co.kr.jurumarble.interceptor;

import co.kr.jurumarble.token.domain.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    // 컨트롤러 호출전에 호출
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            parseTokenAndTransferUserId(request, authorizationHeader);
        } catch (JwtException | IllegalArgumentException exception) {
            log.info("jwtException : {}", exception);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증에 실패하였습니다.");
            return false;
        }
        return true;
    }

    private void parseTokenAndTransferUserId(HttpServletRequest request, String authorizationHeader) {
        HashMap<String, Object> parseJwtTokenMap = jwtTokenProvider.parseJwtToken(authorizationHeader);
        Long userId = getUserIdFromToken(parseJwtTokenMap);
        request.setAttribute("userId", userId);
    }

    private static Long getUserIdFromToken(HashMap<String, Object> parseJwtTokenMap) {
        Claims claims = (Claims) parseJwtTokenMap.get("claims");
        Integer integerUserId = (Integer) claims.get("userId");
        return Long.valueOf(integerUserId);
    }
}
