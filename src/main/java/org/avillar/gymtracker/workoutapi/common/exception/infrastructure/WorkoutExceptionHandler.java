package org.avillar.gymtracker.workoutapi.common.exception.infrastructure;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.common.errors.infrastructure.model.ApiError;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutForDateAlreadyExistsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class WorkoutExceptionHandler {

  private static final String MSG_DUPLICATED_WORKOUT_DATE_ERROR =
      "There is already a workout on that date for that user";

  @ExceptionHandler(WorkoutForDateAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ApiError handleDuplicatedWorkoutDate(final WorkoutForDateAlreadyExistsException ex) {
    log.debug(
        "There is already a workout with date {} for user {}", ex.getWorkoutDate(), ex.getUserId());
    return new ApiError(MSG_DUPLICATED_WORKOUT_DATE_ERROR, ex);
  }

  @ExceptionHandler(ExerciseUnavailableException.class)
  protected ResponseEntity<ApiError> handleExerciseNotFoundException(
      final ExerciseUnavailableException ex) {
    final ApiError apiError;
    final HttpStatus responseCode;
    final UUID exerciseId = ex.getExerciseId();
    switch (ex.getAccessError()) {
      case NOT_FOUND -> {
        apiError = new ApiError("No exercise found with ID: " + exerciseId, ex);
        responseCode = HttpStatus.BAD_REQUEST;
      }
      case NOT_ACCESS -> {
        apiError =
            new ApiError(
                "Do not have permission to access the exercise with the ID: " + exerciseId, ex);
        responseCode = HttpStatus.BAD_REQUEST;
      }
      case UNKNOWN -> {
        apiError =
            new ApiError("The exercise could not be accessed with the ID: " + exerciseId, ex);
        responseCode = HttpStatus.NOT_ACCEPTABLE;
      }
      default -> {
        apiError = new ApiError("Error accessing the exercise with ID: " + exerciseId, ex);
        responseCode = HttpStatus.INTERNAL_SERVER_ERROR;
      }
    }

    log.info("Error accessing the exercise with ID {}: {}", exerciseId, ex.getLocalizedMessage());
    return new ResponseEntity<>(apiError, responseCode);
  }
}
