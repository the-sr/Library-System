package library.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessage> handleException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getDto());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleException(DataIntegrityViolationException e) {
        String message = Objects.requireNonNull(e.getRootCause()).getMessage();
        if (message.contains("Detail:"))
            message = message.substring(message.indexOf("Detail:") + 11);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorMessage.builder().statusCode(HttpStatus.CONFLICT.value()).message(message).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleException(MethodArgumentNotValidException e) {
        AtomicReference<String> message = new AtomicReference<>("");
        e.getBindingResult().getAllErrors().forEach(error -> {
            message.set(message + error.getDefaultMessage() + ", ");
        });
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ErrorMessage.builder()
                .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value()).message(String.valueOf(message)).build());
    }

    @ExceptionHandler(PermissionDeniedDataAccessException.class)
    public ResponseEntity<ErrorMessage> handleException(PermissionDeniedDataAccessException e) {
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorMessage.builder().statusCode(HttpStatus.UNAUTHORIZED.value()).message(message).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorMessage.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(message).build());
    }
}
