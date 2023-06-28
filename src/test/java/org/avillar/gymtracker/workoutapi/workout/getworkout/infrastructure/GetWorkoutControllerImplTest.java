package org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.workout.getworkout.application.GetWorkoutService;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.model.GetWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.mapper.GetWorkoutControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.model.GetWorkoutResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetWorkoutControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  private GetWorkoutControllerImpl getWorkoutControllerImpl;

  @Mock private GetWorkoutService getWorkoutServiceService;
  @Spy private GetWorkoutControllerMapperImpl getWorkoutSimpleControllerMapper;

  @BeforeEach
  void beforeAll() {
    getWorkoutControllerImpl =
        new GetWorkoutControllerImpl(getWorkoutServiceService, getWorkoutSimpleControllerMapper);
  }

  @Test
  void get() {
    final GetWorkoutResponseApplication getWorkoutResponseApplication =
        easyRandom.nextObject(GetWorkoutResponseApplication.class);

    when(getWorkoutServiceService.execute(getWorkoutResponseApplication.getId()))
        .thenReturn(getWorkoutResponseApplication);

    final GetWorkoutResponseInfrastructure getWorkoutResponseInfrastructure =
        getWorkoutControllerImpl.get(getWorkoutResponseApplication.getId()).getBody();
    assertEquals(getWorkoutResponseApplication.getId(), getWorkoutResponseInfrastructure.getId());
    assertEquals(
        getWorkoutResponseApplication.getUserId(), getWorkoutResponseInfrastructure.getUserId());
    assertEquals(
        getWorkoutResponseApplication.getDescription(),
        getWorkoutResponseInfrastructure.getDescription());
    assertEquals(
        getWorkoutResponseApplication.getDate(), getWorkoutResponseInfrastructure.getDate());
  }
}
