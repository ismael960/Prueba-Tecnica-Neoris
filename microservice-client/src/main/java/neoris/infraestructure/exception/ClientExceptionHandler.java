package neoris.infraestructure.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClientExceptionHandler {

    @ExceptionHandler({
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
    })
    public ResponseEntity<String> validDtos() {
        return ResponseEntity.badRequest().body("Validation input params");
    }

    @ExceptionHandler({
            DataIntegrityViolationException.class
    })
    public ResponseEntity<String> validIdentifier() {
        return ResponseEntity.badRequest().body("Validation identifier");
    }
}
