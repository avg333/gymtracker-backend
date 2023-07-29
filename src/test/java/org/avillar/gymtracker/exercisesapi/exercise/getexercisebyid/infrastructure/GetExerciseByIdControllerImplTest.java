package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.GetExerciseByIdService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.model.GetExerciseByIdResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.mapper.GetExerciseByIdControllerMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetExerciseByIdControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetExerciseByIdControllerImpl getExerciseByIdController;

  @Mock private GetExerciseByIdService getExerciseByIdService;

  @Spy
  private final GetExerciseByIdControllerMapper getExerciseByIdControllerMapper =
      Mappers.getMapper(GetExerciseByIdControllerMapper.class);

  @Test
  void get() {
    final GetExerciseByIdResponseApplication expected =
        easyRandom.nextObject(GetExerciseByIdResponseApplication.class);
    final UUID exerciseId = expected.getId();

    when(getExerciseByIdService.execute(exerciseId)).thenReturn(expected);

    assertThat(getExerciseByIdController.execute(exerciseId))
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }
}
