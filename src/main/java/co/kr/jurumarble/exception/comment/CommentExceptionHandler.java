package co.kr.jurumarble.exception.comment;


import co.kr.jurumarble.exception.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CommentExceptionHandler {

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handle(CommentNotFoundException e) {
        return ResponseEntity.status(e.getStatus().getStatusCode())
                .body(ExceptionMessage.of(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(CommentEmotionNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handle(CommentEmotionNotFoundException e) {
        return ResponseEntity.status(e.getStatus().getStatusCode())
                .body(ExceptionMessage.of(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(NestedCommentNotAllowedException.class)
    public ResponseEntity<ExceptionMessage> handle(NestedCommentNotAllowedException e) {
        return ResponseEntity.status(e.getStatus().getStatusCode())
                .body(ExceptionMessage.of(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(InvalidSortingMethodException.class)
    public ResponseEntity<ExceptionMessage> handle(InvalidSortingMethodException e) {
        return ResponseEntity.status(e.getStatus().getStatusCode())
                .body(ExceptionMessage.of(e.getStatus(), e.getMessage()));
    }
}