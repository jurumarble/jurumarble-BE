package co.kr.jurumarble.exception.report;

import co.kr.jurumarble.exception.ExceptionMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotificationExceptionHandler {

    @ExceptionHandler(SelfReportNotAllowedException.class)
    public ResponseEntity<ExceptionMessage> handle(SelfReportNotAllowedException e) {
        return ResponseEntity.status(e.getStatus().getStatusCode())
                .body(ExceptionMessage.of(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(DuplicateReportException.class)
    public ResponseEntity<ExceptionMessage> handle(DuplicateReportException e) {
        return ResponseEntity.status(e.getStatus().getStatusCode())
                .body(ExceptionMessage.of(e.getStatus(), e.getMessage()));
    }
}
