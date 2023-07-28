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
import org.avillar.gymtracker.common.errors.infrastructure.RestExceptionHandler.ApiError;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private RestExceptionHandler restExceptionHandler;

  @Test
  void testHandleDuplicatedWorkoutDate() {
    assertEquals(
        "There is already a workout on that date for that user",
        restExceptionHandler
            .handleDuplicatedWorkoutDate(
                new DuplicatedWorkoutDateException(UUID.randomUUID(), new Date()))
            .getMessage());
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
    assertEquals(
        "Entity not found",
        restExceptionHandler
            .handleEntityNotFound(new EntityNotFoundException(Workout.class, UUID.randomUUID()))
            .getMessage());
  }

  @Test
  void handleIllegalAccessException() {
    assertEquals(
        "You do not have permissions to access the resource",
        restExceptionHandler
            .handleIllegalAccessException(
                new IllegalAccessException(
                    easyRandom.nextObject(Workout.class), AuthOperations.READ, UUID.randomUUID()))
            .getMessage());
  }

  @Test
  void testHandleException() {
    assertEquals(
        "Internal server error",
        restExceptionHandler.handleException(new Exception()).getMessage());
  }
}
