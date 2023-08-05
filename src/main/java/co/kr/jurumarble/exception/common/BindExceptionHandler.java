package co.kr.jurumarble.exception.common;

import co.kr.jurumarble.exception.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BindExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionMessage> bindException(BindException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessage.of(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }
}