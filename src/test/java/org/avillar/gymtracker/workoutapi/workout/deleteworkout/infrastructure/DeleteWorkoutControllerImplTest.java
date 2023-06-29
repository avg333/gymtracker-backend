package org.avillar.gymtracker.workoutapi.workout.deleteworkout.infrastructure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.deleteworkout.application.DeleteWorkoutService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class DeleteWorkoutControllerImplTest {

  @InjectMocks private DeleteWorkoutControllerImpl deleteWorkoutController;

  @Mock private DeleteWorkoutService deleteWorkoutService;

  @Test
  void deleteWorkout() {
    final UUID workoutId = UUID.randomUUID();

    doNothing().when(deleteWorkoutService).execute(workoutId);

    final ResponseEntity<Void> response =
        assertDoesNotThrow(() -> deleteWorkoutController.execute(workoutId));
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertNull(response.getBody());
    verify(deleteWorkoutService).execute(workoutId);
  }
}
