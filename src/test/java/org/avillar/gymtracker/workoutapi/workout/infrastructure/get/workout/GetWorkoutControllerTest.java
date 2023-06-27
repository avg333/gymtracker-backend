package org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workout;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.GetWorkoutService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.model.GetWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.GetWorkoutController;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.mapper.GetWorkoutControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetWorkoutControllerTest {

  private final EasyRandom easyRandom = new EasyRandom();

  private GetWorkoutController getWorkoutController;

  @Mock private GetWorkoutService getWorkoutService;
  @Spy private GetWorkoutControllerMapperImpl getWorkoutControllerMapper;

  @BeforeEach
  void beforeAll() {
    getWorkoutController = new GetWorkoutController(getWorkoutService, getWorkoutControllerMapper);
  }

  @Test
  void get() {
    final GetWorkoutResponseApplication getWorkoutResponseApplication =
        easyRandom.nextObject(GetWorkoutResponseApplication.class);

    when(getWorkoutService.execute(getWorkoutResponseApplication.getId()))
        .thenReturn(getWorkoutResponseApplication);

    final GetWorkoutResponseInfrastructure getWorkoutResponseInfrastructure =
        getWorkoutController.get(getWorkoutResponseApplication.getId()).getBody();
    assertEquals(getWorkoutResponseApplication.getId(), getWorkoutResponseInfrastructure.getId());
    assertEquals(
        getWorkoutResponseApplication.getUserId(), getWorkoutResponseInfrastructure.getUserId());
    assertEquals(
        getWorkoutResponseApplication.getDescription(),
        getWorkoutResponseInfrastructure.getDescription());
    assertEquals(
        getWorkoutResponseApplication.getDate(), getWorkoutResponseInfrastructure.getDate());
    assertEquals(
        getWorkoutResponseApplication.getDate(), getWorkoutResponseInfrastructure.getDate());
    assertEquals(
        getWorkoutResponseApplication.getSetGroups().size(),
        getWorkoutResponseInfrastructure.getSetGroups().size());
  }
}
