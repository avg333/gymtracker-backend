package org.avillar.gymtracker.common.errors.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.RedisConnectionFailureException;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private RestExceptionHandler restExceptionHandler;

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
  void testHandleRedisConnectionException() {
    assertEquals(
        "Redis connection error",
        restExceptionHandler
            .handleRedisConnectionException(
                new RedisConnectionFailureException(easyRandom.nextObject(String.class)))
            .getMessage());
  }

  @Test
  void testHandleException() {
    assertEquals(
        "Internal server error",
        restExceptionHandler.handleException(new Exception()).getMessage());
  }
}
