package co.kr.jurumarble.interceptor;

import co.kr.jurumarble.token.domain.JwtTokenProvider;
import io.jsonwebtoken.Claims;
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
public class TokenInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    // 컨트롤러 호출전에 호출
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        parseTokenAndTransferUserId(request, authorizationHeader);
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
