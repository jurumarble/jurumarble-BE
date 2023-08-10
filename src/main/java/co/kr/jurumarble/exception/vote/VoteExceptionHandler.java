package co.kr.jurumarble.exception.vote;

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
public class VoteExceptionHandler {

    @ExceptionHandler(VoteNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handle(VoteNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessage.of(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(AlreadyUserDoVoteException.class)
    public ResponseEntity<ExceptionMessage> handle(AlreadyUserDoVoteException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ExceptionMessage.of(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(VoteTypeNotMatchException.class)
    public ResponseEntity<ExceptionMessage> handle(VoteTypeNotMatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessage.of(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(VoteDrinksDuplicatedException.class)
    public ResponseEntity<ExceptionMessage> handle(VoteDrinksDuplicatedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessage.of(e.getStatus(), e.getMessage()));
    }
}