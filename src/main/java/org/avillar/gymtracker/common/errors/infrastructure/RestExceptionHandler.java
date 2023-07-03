package org.avillar.gymtracker.common.errors.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.common.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.ExerciseNotFoundException;
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
    final ApiError apiError = new ApiError();
    apiError.setMessage("There is already a workout on that date for that user.");

    if (log.isDebugEnabled()) {
      apiError.setDebugMessage(ex.getLocalizedMessage());
    }

    log.debug(
        "There is already a workout with date {} for user {}", ex.getWorkoutDate(), ex.getUserId());
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ExerciseNotFoundException.class)
  protected ResponseEntity<ApiError> handleDuplicatedWorkoutDate(
      final ExerciseNotFoundException ex) {
    final ApiError apiError = new ApiError();
    final HttpStatus responseCode;
    switch (ex.getAccessError()) {
      case NOT_FOUND -> {
        apiError.setMessage("No exercise found with ID: " + ex.getId());
        responseCode = HttpStatus.NOT_FOUND;
      }
      case NOT_ACCESS -> {
        apiError.setMessage(
            "You do not have permission to access the exercise with the ID: " + ex.getId());
        responseCode = HttpStatus.FORBIDDEN;
      }
      case UNKNOWN -> {
        apiError.setMessage("The exercise could not be accessed with the ID: " + ex.getId());
        responseCode = HttpStatus.NOT_ACCEPTABLE;
      }
      default -> {
        apiError.setMessage("Error accessing the exercise with ID: " + ex.getId());
        responseCode = HttpStatus.INTERNAL_SERVER_ERROR;
      }
    }

    if (log.isDebugEnabled()) {
      apiError.setDebugMessage(ex.getLocalizedMessage());
    }

    log.info("Error accessing the exercise with ID {}", ex.getId());
    return new ResponseEntity<>(apiError, responseCode);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<ApiError> handleEntityNotFound(final EntityNotFoundException ex) {
    final ApiError apiError = new ApiError();
    apiError.setMessage("Objeto no encotrado");

    if (log.isDebugEnabled()) {
      apiError.setDebugMessage(ex.getLocalizedMessage());
    }

    log.debug("No se ha encontrado la entidad {} con el id {}", ex.getClassName(), ex.getId());
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(IllegalAccessException.class)
  protected ResponseEntity<ApiError> handleIllegalAccessException(final IllegalAccessException ex) {
    final ApiError apiError = new ApiError();
    apiError.setMessage("You do not have permissions to access the resource");

    if (log.isDebugEnabled()) {
      apiError.setDebugMessage(ex.getLocalizedMessage());
    }

    log.info(
        "Illegal access to entity {} with id {} by user with id {}",
        ex.getEntityClassName(),
        ex.getEntityId(),
        ex.getCurrentUserId());
    return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(AuthenticationException.class)
  protected ResponseEntity<ApiError> handleIllegalAccessException(
      final AuthenticationException ex) {
    final ApiError apiError = new ApiError();
    apiError.setMessage("An error occurred during authentication");

    if (log.isDebugEnabled()) {
      apiError.setDebugMessage(ex.getLocalizedMessage());
    }

    log.error("Error while trying to login:", ex);
    return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ApiError> handleException(final Exception ex) {
    final ApiError apiError = new ApiError();
    apiError.setMessage("Internal server error");

    if (log.isDebugEnabled()) {
      apiError.setDebugMessage(ex.getLocalizedMessage());
    }

    log.error("Internal server error:", ex);
    return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
