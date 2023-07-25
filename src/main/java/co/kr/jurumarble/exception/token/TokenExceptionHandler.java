package co.kr.jurumarble.exception.token;

import co.kr.jurumarble.exception.ExceptionMessage;
import co.kr.jurumarble.exception.StatusEnum;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class TokenExceptionHandler {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ExceptionMessage> handle(JwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionMessage.of(StatusEnum.TOKEN_EXPIRED, e.getMessage()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionMessage> handle(ExpiredJwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionMessage.of(StatusEnum.TOKEN_EXPIRED, e.getMessage()));
    }
}
