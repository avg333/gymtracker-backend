package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.GetExercisesByFilterService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.mapper.GetExercisesByFilterControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterRequest;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetExercisesByFilterControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetExercisesByFilterControllerImpl getExercisesByFilterController;

  @Mock private GetExercisesByFilterService getExercisesByFilterService;

  @Spy
  private GetExercisesByFilterControllerMapper getExercisesByFilterControllerMapper =
      Mappers.getMapper(GetExercisesByFilterControllerMapper.class);

  @Test
  void get() {
    final GetExercisesByFilterRequest request =
        easyRandom.nextObject(GetExercisesByFilterRequest.class);
    final List<GetExercisesByFilterResponseApplication> expected =
        easyRandom.objects(GetExercisesByFilterResponseApplication.class, 5).toList();

    when(getExercisesByFilterService.execute(getExercisesByFilterControllerMapper.map(request)))
        .thenReturn(expected);

    final List<GetExercisesByFilterResponse> result =
        getExercisesByFilterController.execute(request);
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }
}
