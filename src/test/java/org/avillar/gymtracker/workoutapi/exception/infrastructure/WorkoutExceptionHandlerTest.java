package org.avillar.gymtracker.workoutapi.exception.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.infrastructure.ApiError;
import org.avillar.gymtracker.workoutapi.exception.application.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException.AccessError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class WorkoutExceptionHandlerTest {

  @InjectMocks private WorkoutExceptionHandler restExceptionHandler;

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
}
