package co.kr.jurumarble.interceptor;

import co.kr.jurumarble.token.domain.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    private static final List<String> PERMIT_JWT_URL_ARRAY = List.of("/api/votes/v2");


    @Override
    // 컨트롤러 호출전에 호출
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {  //preflight 통과하도록 설정
            return true;
        }
        String requestURI = request.getRequestURI();

        if(PERMIT_JWT_URL_ARRAY.contains(requestURI) && request.getHeader(HttpHeaders.AUTHORIZATION) == null) {
            request.setAttribute("userId", null);
            log.info("%%%%%%%%%%%%%%%%%" + "if문 진입");
            log.info("%%%%%%%%%%%%%%%%%" + request.getAttribute("userId"));
            return true;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        parseTokenAndTransferUserId(request, authorizationHeader);
        log.info("%%%%%%%%%%%%%%%%%" + request.getAttribute("userId"));
        return true;
    }

    private void parseTokenAndTransferUserId(HttpServletRequest request, String authorizationHeader) {
        HashMap<String, Object> parseJwtTokenMap = jwtTokenProvider.parseJwtToken(request ,authorizationHeader);
        Long userId = getUserIdFromToken(parseJwtTokenMap);
        request.setAttribute("userId", userId);
        log.info("%%%%%%%%%%%%%%%%%" + request.getAttribute("userId"));
    }

    private Long getUserIdFromToken(HashMap<String, Object> parseJwtTokenMap) {
        Claims claims = (Claims) parseJwtTokenMap.get("claims");
        Integer integerUserId = (Integer) claims.get("userId");
        return Long.valueOf(integerUserId);
    }

}
