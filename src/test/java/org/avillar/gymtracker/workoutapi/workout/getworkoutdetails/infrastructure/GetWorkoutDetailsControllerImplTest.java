package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.GetWorkoutDetailsService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.mapper.GetWorkoutDetailsControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponseDto;
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
class GetWorkoutDetailsControllerImplTest {

  @InjectMocks private GetWorkoutDetailsControllerImpl controller;

  @Mock private GetWorkoutDetailsService service;
  @Mock private GetWorkoutDetailsControllerMapper mapper;

  @Test
  void shouldGetWorkoutDetailsSuccessfully()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    final UUID workoutId = UUID.randomUUID();
    final Workout response = Instancio.create(Workout.class);
    final GetWorkoutDetailsResponseDto responseDto =
        Instancio.create(GetWorkoutDetailsResponseDto.class);

    when(service.execute(workoutId)).thenReturn(response);
    when(mapper.map(response)).thenReturn(responseDto);

    assertThat(controller.execute(workoutId)).isEqualTo(responseDto);
  }
}
