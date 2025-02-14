package neoris.shared.exception;

import jakarta.validation.ConstraintViolationException;
import neoris.account.infraestructure.exception.MovementException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MovementExceptionHandler {

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

    @ExceptionHandler({
            MovementException.class
    })
    public ResponseEntity<ErrorDTO> validMovementException(MovementException movementException) {
        return new ResponseEntity<>(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), movementException.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
