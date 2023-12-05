package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.GetExercisesByIdsService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.mapper.GetExercisesByIdsControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.model.GetExercisesByIdsResponse;
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
class GetExercisesByIdsControllerImplTest {

  @InjectMocks private GetExercisesByIdsControllerImpl getExercisesByIdsController;

  @Mock private GetExercisesByIdsService getExercisesByIdsService;
  @Mock private GetExercisesByIdsControllerMapper getExercisesByIdsControllerMapper;

  @Test
  void shouldGetExercisesByIdsSuccessfully() throws IllegalAccessException {
    final Set<UUID> request = Instancio.createSet(UUID.class);
    final List<Exercise> serviceResponse = Instancio.createList(Exercise.class);
    final List<GetExercisesByIdsResponse> mapperResponse =
        Instancio.createList(GetExercisesByIdsResponse.class);

    when(getExercisesByIdsService.execute(request)).thenReturn(serviceResponse);
    when(getExercisesByIdsControllerMapper.map(serviceResponse)).thenReturn(mapperResponse);

    assertThat(getExercisesByIdsController.execute(request)).isEqualTo(mapperResponse);
  }
}
