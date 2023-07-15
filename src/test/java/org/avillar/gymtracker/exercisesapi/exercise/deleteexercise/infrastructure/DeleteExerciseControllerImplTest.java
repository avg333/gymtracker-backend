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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class DeleteExerciseControllerImplTest {

  @InjectMocks private DeleteExerciseControllerImpl deleteExerciseController;

  @Mock private DeleteExerciseService deleteExerciseService;

  @Test
  void deleteOk() {
    final UUID exerciseId = UUID.randomUUID();

    doNothing().when(deleteExerciseService).execute(exerciseId);

    final ResponseEntity<Void> response =
        assertDoesNotThrow(() -> deleteExerciseController.execute(exerciseId));
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertNull(response.getBody());
  }
}
