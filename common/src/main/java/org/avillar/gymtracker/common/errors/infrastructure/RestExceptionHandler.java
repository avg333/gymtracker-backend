package org.avillar.gymtracker.common.errors.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.common.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DuplicatedWorkoutDateException.class)
  protected ResponseEntity<ApiError> handleDuplicatedWorkoutDate(
      final DuplicatedWorkoutDateException ex) {
    final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
    apiError.setMessage("Objeto no encotrado");

    if (log.isDebugEnabled()) {
      apiError.setDebugMessage(ex.getLocalizedMessage());
    }

    log.debug(
        "Ya existe un org.avillar.gymtracker.workoutapi.workout con fecha {} para el usuario {}",
        ex.getWorkoutDate(),
        ex.getUserId());
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<ApiError> handleEntityNotFound(final EntityNotFoundException ex) {
    final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
    apiError.setMessage("Objeto no encotrado");

    if (log.isDebugEnabled()) {
      apiError.setDebugMessage(ex.getLocalizedMessage());
    }

    log.debug("No se ha encontrado la entidad {} con el id {}", ex.getClassName(), ex.getId());
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ExceptionHandler(IllegalAccessException.class)
  protected ResponseEntity<ApiError> handleIllegalAccessException(final IllegalAccessException ex) {
    final ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
    apiError.setMessage("No hay permisos para acceder al objeto");

    if (log.isDebugEnabled()) {
      apiError.setDebugMessage(ex.getLocalizedMessage());
    }

    log.info(
        "Acceso ilegal a la entidad {} con id {} por el usuario con id {}",
        ex.getEntityClassName(),
        ex.getEntityId(),
        ex.getCurrentUserId());
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ExceptionHandler(AuthenticationException.class)
  protected ResponseEntity<ApiError> handleIllegalAccessException(
      final AuthenticationException ex) {
    final ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
    apiError.setMessage("No hay permisos para acceder al objeto");

    if (log.isDebugEnabled()) {
      apiError.setDebugMessage(ex.getLocalizedMessage());
    }

    log.error("Error al tratar de hacer login:", ex);
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ApiError> handleException(final Exception ex) {
    final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
    apiError.setMessage("Error en el servidor");

    if (log.isDebugEnabled()) {
      apiError.setDebugMessage(ex.getLocalizedMessage());
    }

    log.error("Error no controlado:", ex);
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
