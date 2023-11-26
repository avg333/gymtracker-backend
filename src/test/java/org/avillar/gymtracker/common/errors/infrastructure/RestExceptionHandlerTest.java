package org.avillar.gymtracker.common.errors.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.RedisConnectionFailureException;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {

  @InjectMocks private RestExceptionHandler restExceptionHandler;

  @Test
  void handleEntityNotFound() {
    assertEquals(
        "Entity not found",
        restExceptionHandler
            .handleEntityNotFound(
                new EntityNotFoundException(WorkoutEntity.class, "id", UUID.randomUUID()))
            .getMessage());
  }

  @Test
  void handleIllegalAccessException() {
    WorkoutEntity workoutEntity = Instancio.create(WorkoutEntity.class);
    assertEquals(
        "You do not have permissions to access the resource",
        restExceptionHandler
            .handleIllegalAccessException(
                new IllegalAccessException(
                    WorkoutEntity.class,
                    workoutEntity.getId(),
                    AuthOperations.READ,
                    UUID.randomUUID()))
            .getMessage());
  }

  @Test
  void testHandleRedisConnectionException() {
    assertEquals(
        "Redis connection error",
        restExceptionHandler
            .handleRedisConnectionException(
                new RedisConnectionFailureException(Instancio.create(String.class)))
            .getMessage());
  }

  @Test
  void testHandleException() {
    assertEquals(
        "Internal server error",
        restExceptionHandler.handleException(new Exception()).getMessage());
  }
}
