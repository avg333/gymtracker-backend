package org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.CreateExerciseService;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.mapper.CreteExerciseControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseRequest;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseResponse;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponseDto.SetGroup.Exercise;
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
class CreateExerciseControllerImplTest {

  @InjectMocks private CreateExerciseControllerImpl createExerciseController;

  @Mock private CreateExerciseService createExerciseService;
  @Mock private CreteExerciseControllerMapper creteExerciseControllerMapper;

  @Test
  void shouldCreateExerciseOk() throws EntityNotFoundException, IllegalAccessException {
    final UUID userId = UUID.randomUUID();
    final CreateExerciseRequest requestDto = Instancio.create(CreateExerciseRequest.class);
    final Exercise request = Instancio.create(Exercise.class);
    final Exercise response = Instancio.create(Exercise.class);
    final CreateExerciseResponse responseDto = Instancio.create(CreateExerciseResponse.class);

    when(creteExerciseControllerMapper.map(requestDto)).thenReturn(request);
    when(createExerciseService.execute(userId, request)).thenReturn(response);
    when(creteExerciseControllerMapper.map(response)).thenReturn(responseDto);

    assertThat(createExerciseController.execute(userId, requestDto)).isEqualTo(responseDto);
  }
}
