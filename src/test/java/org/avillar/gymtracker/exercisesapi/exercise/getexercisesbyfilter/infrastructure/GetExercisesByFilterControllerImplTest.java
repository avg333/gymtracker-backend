package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.GetExercisesByFilterService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterRequestApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.mapper.GetExercisesByFilterControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterRequest;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponse;
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
class GetExercisesByFilterControllerImplTest {

  private static final int LIST_SIZE = 5;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetExercisesByFilterControllerImpl getExercisesByFilterController;

  @Mock private GetExercisesByFilterService getExercisesByFilterService;

  @Spy
  private final GetExercisesByFilterControllerMapper getExercisesByFilterControllerMapper =
      Mappers.getMapper(GetExercisesByFilterControllerMapper.class);

  @Test
  void get() {
    final GetExercisesByFilterRequest request =
        easyRandom.nextObject(GetExercisesByFilterRequest.class);
    final List<GetExercisesByFilterResponseApplication> expected =
        easyRandom.objects(GetExercisesByFilterResponseApplication.class, LIST_SIZE).toList();

    final ArgumentCaptor<GetExercisesByFilterRequestApplication>
        getExercisesByFilterRequestApplicationCaptor =
            ArgumentCaptor.forClass(GetExercisesByFilterRequestApplication.class);

    when(getExercisesByFilterService.execute(
            getExercisesByFilterRequestApplicationCaptor.capture()))
        .thenReturn(expected);

    final List<GetExercisesByFilterResponse> result =
        getExercisesByFilterController.execute(request);
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    assertThat(getExercisesByFilterRequestApplicationCaptor.getValue())
        .usingRecursiveComparison()
        .isEqualTo(request);
  }
}
