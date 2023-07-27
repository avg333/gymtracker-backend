package org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.application.DeleteExerciseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteExerciseControllerImplTest {

  @InjectMocks private DeleteExerciseControllerImpl deleteExerciseController;

  @Mock private DeleteExerciseService deleteExerciseService;

  @Test
  void deleteOk() {
    final UUID exerciseId = UUID.randomUUID();

    doNothing().when(deleteExerciseService).execute(exerciseId);

    assertNull(assertDoesNotThrow(() -> deleteExerciseController.execute(exerciseId)));
  }
}
