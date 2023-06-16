package org.avillar.gymtracker.workoutapi.workout.infrastructure.delete;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.application.delete.DeleteWorkoutService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteWorkoutControllerTest {

  @InjectMocks private DeleteWorkoutController deleteWorkoutController;

  @Mock private DeleteWorkoutService deleteWorkoutService;

  @Test
  void deleteWorkout() {
    final UUID userId = UUID.randomUUID();
    Mockito.doNothing().when(deleteWorkoutService).delete(userId);
    Assertions.assertDoesNotThrow(() -> deleteWorkoutController.deleteWorkout(userId));
  }
}
