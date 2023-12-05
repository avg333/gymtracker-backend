package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.GetExercisesFilter;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.GetExercisesByFilterService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.mapper.GetExercisesByFilterControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterRequest;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponse;
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
class GetExercisesByFilterControllerImplTest {

  @InjectMocks private GetExercisesByFilterControllerImpl getExercisesByFilterController;

  @Mock private GetExercisesByFilterService getExercisesByFilterService;
  @Mock private GetExercisesByFilterControllerMapper getExercisesByFilterControllerMapper;

  @Test
  void shouldGetExercisesByFilterSuccessfully() throws ExerciseIllegalAccessException {
    final GetExercisesByFilterRequest request = Instancio.create(GetExercisesByFilterRequest.class);
    final GetExercisesFilter serviceRequest = Instancio.create(GetExercisesFilter.class);
    final List<Exercise> serviceResponse = Instancio.createList(Exercise.class);
    final List<GetExercisesByFilterResponse> mapperResponse =
        Instancio.createList(GetExercisesByFilterResponse.class);

    when(getExercisesByFilterControllerMapper.map(request)).thenReturn(serviceRequest);
    when(getExercisesByFilterService.execute(serviceRequest)).thenReturn(serviceResponse);
    when(getExercisesByFilterControllerMapper.map(serviceResponse)).thenReturn(mapperResponse);

    assertThat(getExercisesByFilterController.execute(request)).isEqualTo(mapperResponse);
  }
}
