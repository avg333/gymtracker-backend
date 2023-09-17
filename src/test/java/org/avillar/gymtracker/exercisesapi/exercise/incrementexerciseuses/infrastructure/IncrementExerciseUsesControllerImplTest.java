package org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.application.IncrementExerciseUsesService;
import org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.application.model.IncrementExerciseUsesResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.infrastructure.mapper.IncrementExerciseUsesControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.infrastructure.model.IncrementExerciseUsesResponseInfrastructure;
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
class IncrementExerciseUsesControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private IncrementExerciseUsesControllerImpl incrementExerciseUsesController;

  @Mock private IncrementExerciseUsesService incrementExerciseUsesService;

  @Spy
  private IncrementExerciseUsesControllerMapper incrementExerciseUsesControllerMapper =
      Mappers.getMapper(IncrementExerciseUsesControllerMapper.class);

  @Test
  void executeTest() {
    final UUID exerciseId = easyRandom.nextObject(UUID.class);
    final IncrementExerciseUsesResponseApplication expected =
        easyRandom.nextObject(IncrementExerciseUsesResponseApplication.class);

    when(incrementExerciseUsesService.execute(exerciseId)).thenReturn(expected);

    final IncrementExerciseUsesResponseInfrastructure result =
        incrementExerciseUsesController.execute(exerciseId);
    assertThat(result).isNotNull();
    assertThat(result.getUses()).isEqualTo(expected.getUses());
  }
}
