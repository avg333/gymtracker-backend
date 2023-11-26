package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutForDateAlreadyExistsException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.CreateWorkoutService;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.mapper.CreateWorkoutControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutRequest;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class CreateWorkoutControllerImplTest {

  @InjectMocks private CreateWorkoutControllerImpl controller;

  @Mock private CreateWorkoutService service;
  @Mock private CreateWorkoutControllerMapper mapper;

  @Test
  void shouldCreateWorkoutSuccessfully()
      throws WorkoutForDateAlreadyExistsException, WorkoutIllegalAccessException {
    final UUID userId = UUID.randomUUID();
    final CreateWorkoutRequest requestDto = Instancio.create(CreateWorkoutRequest.class);
    final Workout request = Instancio.create(Workout.class);
    final Workout response = Instancio.create(Workout.class);
    final CreateWorkoutResponse responseDto = Instancio.create(CreateWorkoutResponse.class);

    when(mapper.map(requestDto)).thenReturn(request);
    when(service.execute(userId, request)).thenReturn(response);
    when(mapper.map(response)).thenReturn(responseDto);

    assertThat(controller.execute(userId, requestDto)).isEqualTo(responseDto);
  }
}
