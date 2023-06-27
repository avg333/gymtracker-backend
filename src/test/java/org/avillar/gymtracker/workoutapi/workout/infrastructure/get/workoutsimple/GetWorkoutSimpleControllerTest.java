package org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workoutsimple;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.workout.getworkout.application.GetWorkoutSimpleService;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.model.GetWorkoutSimpleResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.GetWorkoutSimpleController;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.mapper.GetWorkoutSimpleControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.model.GetWorkoutSimpleResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetWorkoutSimpleControllerTest {

  private final EasyRandom easyRandom = new EasyRandom();

  private GetWorkoutSimpleController getWorkoutSimpleController;

  @Mock private GetWorkoutSimpleService getWorkoutSimpleServiceService;
  @Spy private GetWorkoutSimpleControllerMapperImpl getWorkoutSimpleControllerMapper;

  @BeforeEach
  void beforeAll() {
    getWorkoutSimpleController =
        new GetWorkoutSimpleController(
            getWorkoutSimpleServiceService, getWorkoutSimpleControllerMapper);
  }

  @Test
  void get() {
    final GetWorkoutSimpleResponseApplication getWorkoutSimpleResponseApplication =
        easyRandom.nextObject(GetWorkoutSimpleResponseApplication.class);

    when(getWorkoutSimpleServiceService.execute(getWorkoutSimpleResponseApplication.getId()))
        .thenReturn(getWorkoutSimpleResponseApplication);

    final GetWorkoutSimpleResponseInfrastructure getWorkoutSimpleResponseInfrastructure =
        getWorkoutSimpleController.get(getWorkoutSimpleResponseApplication.getId()).getBody();
    assertEquals(
        getWorkoutSimpleResponseApplication.getId(),
        getWorkoutSimpleResponseInfrastructure.getId());
    assertEquals(
        getWorkoutSimpleResponseApplication.getUserId(),
        getWorkoutSimpleResponseInfrastructure.getUserId());
    assertEquals(
        getWorkoutSimpleResponseApplication.getDescription(),
        getWorkoutSimpleResponseInfrastructure.getDescription());
    assertEquals(
        getWorkoutSimpleResponseApplication.getDate(),
        getWorkoutSimpleResponseInfrastructure.getDate());
  }
}
