package co.kr.jurumarble.exception.common;

import co.kr.jurumarble.exception.ExceptionMessage;
import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.token.TokenNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionMessage> handle(CustomException e) {
        int statusCode = e.getStatus().getStatusCode();
        return ResponseEntity.status(HttpStatus.valueOf(statusCode))
                .body(ExceptionMessage.of(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionMessage> bindException(BindException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessage.of(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

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

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handle(TokenNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionMessage.of(StatusEnum.TOKEN_EXPIRED, e.getMessage()));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ExceptionMessage> handle(EmptyResultDataAccessException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionMessage.of(StatusEnum.TOKEN_EXPIRED, "해당 아이디를 가진 유저가 없습니다. 아이디 값을 다시 한번 확인하세요."));
    }
}
