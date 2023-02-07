package org.avillar.gymtracker.errors.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.errors.application.exceptions.BadFormException;
import org.avillar.gymtracker.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.exceptions.IllegalAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.avillar.gymtracker.errors.application.ErrorCodes.*;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadFormException.class)
    protected ResponseEntity<ApiError> handleBadForm(final BadFormException ex) {
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ERR_400.name());
        apiError.addValidationError(ex.getClassName(), ex.getObjectErrors());

        if (log.isDebugEnabled()) {
            apiError.setDebugMessage(ex.getLocalizedMessage());
        }

        log.debug("Clase {} mal formada. Nº de errores: {}", ex.getClassName(), ex.getObjectErrors().size());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ApiError> handleEntityNotFound(final EntityNotFoundException ex) {
        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ERR_404.name());

        if (log.isDebugEnabled()) {
            apiError.setDebugMessage(ex.getLocalizedMessage());
        }

        log.debug("No se ha encontrado la entidad {} con el id {}", ex.getClassName(), ex.getId());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(IllegalAccessException.class)
    protected ResponseEntity<ApiError> handleIllegalAccessException(final IllegalAccessException ex) {
        final ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
        apiError.setMessage(ERR_403.name());

        if (log.isDebugEnabled()) {
            apiError.setDebugMessage(ex.getLocalizedMessage());
        }

        log.info("Acceso ilegal a la entidad {} con id {} por el usuario con id {}", ex.getEntityClassName(), ex.getEntityId(), ex.getCurrentUserId());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiError> handleException(final Exception ex) {
        final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage(ERR_500.defaultMessage);

        if (log.isDebugEnabled()) {
            apiError.setDebugMessage(ex.getLocalizedMessage());
        }

        log.error("Error no controlado:", ex);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
