package hei.ingredient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;


@RestControllerAdvice
public class GlobalHandlerException extends RuntimeException {


    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of(
                            "timestamp", Instant.now(),
                            "status", 400,
                            "error", "Bad Request",
                            "message", ex.getMessage()
                    )
            );
        }

        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of(
                            "timestamp", Instant.now(),
                            "status", 404,
                            "error", "Not Found",
                            "message", ex.getMessage()
                    )
            );
        }

        @ExceptionHandler(ConflictException.class)
        public ResponseEntity<Map<String, Object>> handleConflict(ConflictException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of(
                            "timestamp", Instant.now(),
                            "status", 409,
                            "error", "Conflict",
                            "message", ex.getMessage()
                    )
            );
        }

        @ExceptionHandler(NullPriceException.class)
        public ResponseEntity<Map<String, Object>> handleNullPrice(NullPriceException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of(
                            "timestamp", Instant.now(),
                            "status", 400,
                            "error", "Null Price",
                            "message", ex.getMessage()
                    )
            );
        }
    }
}
