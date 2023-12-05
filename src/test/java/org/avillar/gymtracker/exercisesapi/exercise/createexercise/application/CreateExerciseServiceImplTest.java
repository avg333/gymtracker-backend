package org.avillar.gymtracker.exercisesapi.exercise.createexercise.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.apache.commons.lang3.NotImplementedException;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class CreateExerciseServiceImplTest {

  @InjectMocks private CreateExerciseServiceImpl createExerciseService;

  @Test
  void shouldThrowNotImplementedException() {
    final Exercise exercise = Instancio.create(Exercise.class);
    final UUID userId = Instancio.create(UUID.class);

    assertThatThrownBy(() -> createExerciseService.execute(userId, exercise))
        .isNotNull()
        .isInstanceOf(NotImplementedException.class);
  }
}
