package org.avillar.gymtracker.common.errors.infrastructure;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.common.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.ExerciseNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.common.errors.application.exceptions.RegisterExcepcion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String MSG_ERROR_REGISTER_PROCESS_ERROR = "Error in the register process";

  private static final String MSG_DUPLICATED_WORKOUT_DATE_ERROR =
      "There is already a workout on that date for that user";
  private static final String MSG_NOT_FOUND_ERROR = "Entity not found";
  private static final String MSG_NOT_PERMISSION_ERROR =
      "You do not have permissions to access the resource";
  private static final String MSG_AUTH_ERROR = "An error occurred during authentication";
  private static final String MSG_INTERNAL_SERVER_ERROR = "Internal server error";

  @ExceptionHandler(RegisterExcepcion.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ApiError handleDuplicatedWorkoutDate(final RegisterExcepcion ex) {
    log.debug("Error in the registration process: {}", ex.getLocalizedMessage());
    return new ApiError(MSG_ERROR_REGISTER_PROCESS_ERROR, ex);
  }

  @ExceptionHandler(DuplicatedWorkoutDateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ApiError handleDuplicatedWorkoutDate(final DuplicatedWorkoutDateException ex) {
    log.debug(
        "There is already a workout with date {} for user {}", ex.getWorkoutDate(), ex.getUserId());
    return new ApiError(MSG_DUPLICATED_WORKOUT_DATE_ERROR, ex);
  }

  @ExceptionHandler(ExerciseNotFoundException.class)
  protected ResponseEntity<ApiError> handleExerciseNotFoundException(
      final ExerciseNotFoundException ex) {
    final ApiError apiError;
    final HttpStatus responseCode;
    switch (ex.getAccessError()) {
      case NOT_FOUND -> {
        apiError = new ApiError("No exercise found with ID: " + ex.getId(), ex);
        responseCode = HttpStatus.NOT_FOUND;
      }
      case NOT_ACCESS -> {
        apiError =
            new ApiError(
                "You do not have permission to access the exercise with the ID: " + ex.getId(), ex);
        responseCode = HttpStatus.FORBIDDEN;
      }
      case UNKNOWN -> {
        apiError =
            new ApiError("The exercise could not be accessed with the ID: " + ex.getId(), ex);
        responseCode = HttpStatus.NOT_ACCEPTABLE;
      }
      default -> {
        apiError = new ApiError("Error accessing the exercise with ID: " + ex.getId(), ex);
        responseCode = HttpStatus.INTERNAL_SERVER_ERROR;
      }
    }

    log.info("Error accessing the exercise with ID {}: {}", ex.getId(), ex.getLocalizedMessage());
    return new ResponseEntity<>(apiError, responseCode);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  protected ApiError handleEntityNotFound(final EntityNotFoundException ex) {
    log.debug("Entity {} with id {} was not found", ex.getClassName(), ex.getId());
    return new ApiError(MSG_NOT_FOUND_ERROR, ex);
  }

  @ExceptionHandler(IllegalAccessException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  protected ApiError handleIllegalAccessException(final IllegalAccessException ex) {
    log.info(
        "Illegal access to entity {} with id {} by user with id {}",
        ex.getEntityClassName(),
        ex.getEntityId(),
        ex.getCurrentUserId());
    return new ApiError(MSG_NOT_PERMISSION_ERROR, ex);
  }

  @ExceptionHandler(AuthenticationException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  protected ApiError handleIllegalAccessException(final AuthenticationException ex) {
    log.error("Error while trying to login:", ex);
    return new ApiError(MSG_AUTH_ERROR, ex);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ApiError handleException(final Exception ex) {
    log.error("Internal server error:", ex);
    return new ApiError(MSG_INTERNAL_SERVER_ERROR, ex);
  }

  @Getter
  public static class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();

    private final String message;
    private final String debugMessage;

    public ApiError(final String message, final Exception exception) {
      boolean existsException =
          exception != null && StringUtils.isNotEmpty(exception.getLocalizedMessage());

      this.message = message;
      this.debugMessage =
          log.isDebugEnabled() && existsException ? exception.getLocalizedMessage() : null;
    }
  }
}
