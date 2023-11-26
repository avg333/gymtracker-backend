package org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.application.DeleteExerciseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class DeleteExerciseControllerImplTest {

  @InjectMocks private DeleteExerciseControllerImpl deleteExerciseController;

  @Mock private DeleteExerciseService deleteExerciseService;

  @Test
  void deleteOk() throws EntityNotFoundException, IllegalAccessException {
    final UUID exerciseId = UUID.randomUUID();

    doNothing().when(deleteExerciseService).execute(exerciseId);

    assertNull(assertDoesNotThrow(() -> deleteExerciseController.execute(exerciseId)));
  }
}
