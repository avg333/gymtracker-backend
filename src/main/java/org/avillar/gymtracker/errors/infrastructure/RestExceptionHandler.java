package org.avillar.gymtracker.errors.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.errors.application.ApiError;
import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ApiError> handleEntityNotFound(final EntityNotFoundException ex) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        log.debug("No se ha encontrado la entidad {} con el id {}", ex.getClassName(), ex.getId());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(IllegalAccessException.class)
    protected ResponseEntity<ApiError> handleIllegalAccessException(final IllegalAccessException ex) {
        ApiError apiError = new ApiError(FORBIDDEN);
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
