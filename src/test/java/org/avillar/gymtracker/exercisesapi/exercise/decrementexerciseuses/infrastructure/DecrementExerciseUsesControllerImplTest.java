package org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.application.DecrementExerciseUsesService;
import org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.application.model.DecrementExerciseUsesResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.infrastructure.mapper.DecrementExerciseUsesControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.infrastructure.model.DecrementExerciseUsesResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class DecrementExerciseUsesControllerImplTest {
  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private DecrementExerciseUsesControllerImpl decrementExerciseUsesController;

  @Mock private DecrementExerciseUsesService decrementExerciseUsesService;

  @Spy
  private DecrementExerciseUsesControllerMapper decrementExerciseUsesControllerMapper =
      Mappers.getMapper(DecrementExerciseUsesControllerMapper.class);

  @Test
  void executeTest() {
    final UUID exerciseId = easyRandom.nextObject(UUID.class);
    final DecrementExerciseUsesResponseApplication expected =
        easyRandom.nextObject(DecrementExerciseUsesResponseApplication.class);

    when(decrementExerciseUsesService.execute(exerciseId)).thenReturn(expected);

    final DecrementExerciseUsesResponseInfrastructure result =
        decrementExerciseUsesController.execute(exerciseId);
    assertThat(result).isNotNull();
    assertThat(result.getUses()).isEqualTo(expected.getUses());
  }
}
