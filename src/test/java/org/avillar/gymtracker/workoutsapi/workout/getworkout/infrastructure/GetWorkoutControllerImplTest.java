package org.avillar.gymtracker.workoutsapi.workout.getworkout.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.workout.getworkout.application.GetWorkoutService;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.model.GetWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.GetWorkoutControllerImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.mapper.GetWorkoutControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.model.GetWorkoutResponseInfrastructure;
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
class GetWorkoutControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetWorkoutControllerImpl getWorkoutControllerImpl;

  @Mock private GetWorkoutService getWorkoutServiceService;
  @Spy private GetWorkoutControllerMapperImpl getWorkoutSimpleControllerMapper;

  @Test
  void get() {
    final GetWorkoutResponseApplication getWorkoutResponseApplication =
        easyRandom.nextObject(GetWorkoutResponseApplication.class);

    when(getWorkoutServiceService.execute(getWorkoutResponseApplication.getId()))
        .thenReturn(getWorkoutResponseApplication);

    final ResponseEntity<GetWorkoutResponseInfrastructure>
        getWorkoutResponseInfrastructureResponseEntity =
            getWorkoutControllerImpl.execute(getWorkoutResponseApplication.getId());
    assertEquals(HttpStatus.OK, getWorkoutResponseInfrastructureResponseEntity.getStatusCode());
    assertNotNull(getWorkoutResponseInfrastructureResponseEntity.getBody());
    assertEquals(
        getWorkoutResponseApplication.getId(),
        getWorkoutResponseInfrastructureResponseEntity.getBody().getId());
    assertEquals(
        getWorkoutResponseApplication.getUserId(),
        getWorkoutResponseInfrastructureResponseEntity.getBody().getUserId());
    assertEquals(
        getWorkoutResponseApplication.getDescription(),
        getWorkoutResponseInfrastructureResponseEntity.getBody().getDescription());
    assertEquals(
        getWorkoutResponseApplication.getDate(),
        getWorkoutResponseInfrastructureResponseEntity.getBody().getDate());
    verify(getWorkoutServiceService).execute(getWorkoutResponseApplication.getId());
  }
}
