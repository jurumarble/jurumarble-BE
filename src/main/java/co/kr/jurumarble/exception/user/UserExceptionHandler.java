package co.kr.jurumarble.exception.user;

import co.kr.jurumarble.exception.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class UserExceptionHandler {

    @ExceptionHandler(AlreadyExistUserException.class)
    public ResponseEntity<ExceptionMessage> handle(AlreadyExistUserException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessage.of(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handle(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessage.of(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(UserIllegalStateException.class)
    public ResponseEntity<ExceptionMessage> handle(UserIllegalStateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessage.of(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(UserNotAccessRightException.class)
    public ResponseEntity<ExceptionMessage> handle(UserNotAccessRightException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessage.of(e.getStatus(), e.getMessage()));
    }

}