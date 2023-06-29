package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.workout.createworkout.application.CreateWorkoutService;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.mapper.CreateWorkoutControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class CreateWorkoutControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CreateWorkoutControllerImpl postWorkoutControllerImpl;

  @Mock private CreateWorkoutService createWorkoutService;
  @Spy private CreateWorkoutControllerMapperImpl createWorkoutControllerMapper;

  @Test
  void createWorkout() {
    final CreateWorkoutResponseApplication createWorkoutResponseApplication =
        easyRandom.nextObject(CreateWorkoutResponseApplication.class);
    final CreateWorkoutRequestInfrastructure createWorkoutRequestInfrastructure =
        new CreateWorkoutRequestInfrastructure();
    createWorkoutRequestInfrastructure.setDate(createWorkoutResponseApplication.getDate());
    createWorkoutRequestInfrastructure.setDescription(
        createWorkoutResponseApplication.getDescription());

    when(createWorkoutService.execute(
            createWorkoutResponseApplication.getUserId(),
            createWorkoutControllerMapper.map(createWorkoutRequestInfrastructure)))
        .thenReturn(createWorkoutResponseApplication);

    final ResponseEntity<CreateWorkoutResponseInfrastructure> response =
        assertDoesNotThrow(
            () ->
                postWorkoutControllerImpl.execute(
                    createWorkoutResponseApplication.getUserId(),
                    createWorkoutRequestInfrastructure));
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(createWorkoutRequestInfrastructure.getDate(), response.getBody().getDate());
    assertEquals(
        createWorkoutRequestInfrastructure.getDescription(), response.getBody().getDescription());
    assertEquals(createWorkoutResponseApplication.getId(), response.getBody().getId());
    assertEquals(createWorkoutResponseApplication.getUserId(), response.getBody().getUserId());
    verify(createWorkoutService)
        .execute(
            createWorkoutResponseApplication.getUserId(),
            createWorkoutControllerMapper.map(createWorkoutRequestInfrastructure));
  }
}
