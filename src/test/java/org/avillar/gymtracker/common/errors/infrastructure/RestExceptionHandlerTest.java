package org.avillar.gymtracker.common.errors.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.ExerciseNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.ExerciseNotFoundException.AccessError;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private RestExceptionHandler restExceptionHandler;

  @Test
  void handleDuplicatedWorkoutDate() {
    final ResponseEntity<ApiError> result =
        restExceptionHandler.handleDuplicatedWorkoutDate(
            new DuplicatedWorkoutDateException(UUID.randomUUID(), new Date()));
    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(
        "There is already a workout on that date for that user", result.getBody().getMessage());
  }

  @Test
  void testHandleExerciseNotFoundExceptionNotFound() {
    final ExerciseNotFoundException ex =
        new ExerciseNotFoundException(UUID.randomUUID(), AccessError.NOT_FOUND);
    final ResponseEntity<ApiError> result =
        restExceptionHandler.handleExerciseNotFoundException(ex);
    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals("No exercise found with ID: " + ex.getId(), result.getBody().getMessage());
  }

  @Test
  void testHandleExerciseNotFoundExceptionNotAccess() {
    final ExerciseNotFoundException ex =
        new ExerciseNotFoundException(UUID.randomUUID(), AccessError.NOT_ACCESS);
    final ResponseEntity<ApiError> result =
        restExceptionHandler.handleExerciseNotFoundException(ex);
    assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(
        "You do not have permission to access the exercise with the ID: " + ex.getId(),
        result.getBody().getMessage());
  }

  @Test
  void testHandleExerciseNotFoundExceptionUnknown() {
    final ExerciseNotFoundException ex =
        new ExerciseNotFoundException(UUID.randomUUID(), AccessError.UNKNOWN);
    final ResponseEntity<ApiError> result =
        restExceptionHandler.handleExerciseNotFoundException(ex);
    assertEquals(HttpStatus.NOT_ACCEPTABLE, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(
        "The exercise could not be accessed with the ID: " + ex.getId(),
        result.getBody().getMessage());
  }

  @Test
  void handleEntityNotFound() {
    final ResponseEntity<ApiError> result =
        restExceptionHandler.handleEntityNotFound(
            new EntityNotFoundException(Workout.class, UUID.randomUUID()));
    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals("Entity not found", result.getBody().getMessage());
  }

  @Test
  void handleIllegalAccessException() {
    final ResponseEntity<ApiError> result =
        restExceptionHandler.handleIllegalAccessException(
            new IllegalAccessException(
                easyRandom.nextObject(Workout.class), AuthOperations.READ, UUID.randomUUID()));
    assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(
        "You do not have permissions to access the resource", result.getBody().getMessage());
  }

  @Test
  void testHandleIllegalAccessException() {
    class NoOpenPortAuthenticationException extends AuthenticationException {

      public NoOpenPortAuthenticationException(String msg) {
        super(msg);
      }
    }

    final ResponseEntity<ApiError> result =
        restExceptionHandler.handleIllegalAccessException(
            new NoOpenPortAuthenticationException(""));
    assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals("An error occurred during authentication", result.getBody().getMessage());
  }

  @Test
  void handleException() {
    final ResponseEntity<ApiError> result = restExceptionHandler.handleException(new Exception());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals("Internal server error", result.getBody().getMessage());
  }
}
