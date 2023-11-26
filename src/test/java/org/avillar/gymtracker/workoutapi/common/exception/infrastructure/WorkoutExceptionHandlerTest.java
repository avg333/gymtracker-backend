package org.avillar.gymtracker.workoutapi.common.exception.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.infrastructure.model.ApiError;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException.AccessError;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutForDateAlreadyExistsException;
import org.instancio.Instancio;
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
                new WorkoutForDateAlreadyExistsException(
                    UUID.randomUUID(), Instancio.create(LocalDate.class)))
            .getMessage());
  }

  @Test
  void testHandleExerciseNotFoundExceptionNotFound() {
    final ExerciseUnavailableException ex =
        new ExerciseUnavailableException(UUID.randomUUID(), AccessError.NOT_FOUND);
    final ResponseEntity<ApiError> result =
        restExceptionHandler.handleExerciseNotFoundException(ex);
    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals("No exercise found with ID: " + ex.getExerciseId(), result.getBody().getMessage());
  }

  @Test
  void testHandleExerciseNotFoundExceptionNotAccess() {
    final ExerciseUnavailableException ex =
        new ExerciseUnavailableException(UUID.randomUUID(), AccessError.NOT_ACCESS);
    final ResponseEntity<ApiError> result =
        restExceptionHandler.handleExerciseNotFoundException(ex);
    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(
        "Do not have permission to access the exercise with the ID: " + ex.getExerciseId(),
        result.getBody().getMessage());
  }

  @Test
  void testHandleExerciseNotFoundExceptionUnknown() {
    final ExerciseUnavailableException ex =
        new ExerciseUnavailableException(UUID.randomUUID(), AccessError.UNKNOWN);
    final ResponseEntity<ApiError> result =
        restExceptionHandler.handleExerciseNotFoundException(ex);
    assertEquals(HttpStatus.NOT_ACCEPTABLE, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(
        "The exercise could not be accessed with the ID: " + ex.getExerciseId(),
        result.getBody().getMessage());
  }
}
