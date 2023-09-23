package org.avillar.gymtracker.workoutapi.exception.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.common.errors.infrastructure.ApiError;
import org.avillar.gymtracker.workoutapi.exception.application.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order( value = Ordered.HIGHEST_PRECEDENCE )
public class WorkoutExceptionHandler {

  private static final String MSG_DUPLICATED_WORKOUT_DATE_ERROR =
      "There is already a workout on that date for that user";

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
}
