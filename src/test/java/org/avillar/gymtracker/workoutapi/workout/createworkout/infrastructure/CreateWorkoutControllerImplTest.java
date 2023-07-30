package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.workout.createworkout.application.CreateWorkoutService;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutRequestApplication;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.mapper.CreateWorkoutControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutRequest;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class CreateWorkoutControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CreateWorkoutControllerImpl postWorkoutControllerImpl;

  @Mock private CreateWorkoutService createWorkoutService;

  @Spy
  private final CreateWorkoutControllerMapper createWorkoutControllerMapper =
      Mappers.getMapper(CreateWorkoutControllerMapper.class);

  @Test
  void createWorkout() {
    final CreateWorkoutResponseApplication expected =
        easyRandom.nextObject(CreateWorkoutResponseApplication.class);
    final CreateWorkoutRequest createWorkoutRequest = new CreateWorkoutRequest();
    createWorkoutRequest.setDate(expected.getDate());
    createWorkoutRequest.setDescription(expected.getDescription());

    final ArgumentCaptor<CreateWorkoutRequestApplication> createWorkoutRequestApplicationCaptor =
        ArgumentCaptor.forClass(CreateWorkoutRequestApplication.class);

    when(createWorkoutService.execute(
            eq(expected.getUserId()), createWorkoutRequestApplicationCaptor.capture()))
        .thenReturn(expected);

    assertThat(postWorkoutControllerImpl.execute(expected.getUserId(), createWorkoutRequest))
        .usingRecursiveComparison()
        .isEqualTo(expected);
    assertThat(createWorkoutRequestApplicationCaptor.getValue())
        .usingRecursiveComparison()
        .isEqualTo(createWorkoutRequest);
  }
}
