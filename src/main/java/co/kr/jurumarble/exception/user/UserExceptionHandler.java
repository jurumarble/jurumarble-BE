package co.kr.jurumarble.exception.user;

import co.kr.jurumarble.exception.ExceptionMessage;
import co.kr.jurumarble.exception.common.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class UserExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionMessage> handle(CustomException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessage.of(e.getStatus(), e.getMessage()));
    }
}