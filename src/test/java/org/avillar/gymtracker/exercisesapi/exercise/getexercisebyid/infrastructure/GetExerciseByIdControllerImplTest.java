package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.GetExerciseByIdService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.mapper.GetExerciseByIdControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.model.GetExerciseByIdResponse;
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
class GetExerciseByIdControllerImplTest {

  @InjectMocks private GetExerciseByIdControllerImpl getExerciseByIdController;

  @Mock private GetExerciseByIdService getExerciseByIdService;
  @Mock private GetExerciseByIdControllerMapper getExerciseByIdControllerMapper;

  @Test
  void shouldReturnExerciseByIdSuccessfully()
      throws EntityNotFoundException, IllegalAccessException {
    final UUID exerciseId = UUID.randomUUID();
    final Exercise serviceResponse = Instancio.create(Exercise.class);
    final GetExerciseByIdResponse mapperResponse = Instancio.create(GetExerciseByIdResponse.class);

    when(getExerciseByIdService.execute(exerciseId)).thenReturn(serviceResponse);
    when(getExerciseByIdControllerMapper.map(serviceResponse)).thenReturn(mapperResponse);

    assertThat(getExerciseByIdController.execute(exerciseId)).isEqualTo(mapperResponse);
  }
}
