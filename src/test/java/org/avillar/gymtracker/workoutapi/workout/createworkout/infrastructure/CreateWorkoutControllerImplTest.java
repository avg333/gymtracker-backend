package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.workout.createworkout.application.CreateWorkoutService;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.mapper.CreateWorkoutControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutRequest;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutResponse;
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
    final CreateWorkoutResponseApplication expected =
        easyRandom.nextObject(CreateWorkoutResponseApplication.class);
    final CreateWorkoutRequest createWorkoutRequest = new CreateWorkoutRequest();
    createWorkoutRequest.setDate(expected.getDate());
    createWorkoutRequest.setDescription(expected.getDescription());

    when(createWorkoutService.execute(
            expected.getUserId(), createWorkoutControllerMapper.map(createWorkoutRequest)))
        .thenReturn(expected);

    final ResponseEntity<CreateWorkoutResponse> result =
        postWorkoutControllerImpl.execute(expected.getUserId(), createWorkoutRequest);
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody().getId()).isNotNull();
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
