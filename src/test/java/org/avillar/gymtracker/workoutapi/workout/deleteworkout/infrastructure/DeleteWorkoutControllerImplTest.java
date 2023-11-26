package org.avillar.gymtracker.workoutapi.workout.deleteworkout.infrastructure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.deleteworkout.application.DeleteWorkoutService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class DeleteWorkoutControllerImplTest {

  @InjectMocks private DeleteWorkoutControllerImpl controller;

  @Mock private DeleteWorkoutService service;

  @Test
  void shouldDeleteWorkoutSuccessfully()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    final UUID workoutId = UUID.randomUUID();

    doNothing().when(service).execute(workoutId);

    assertNull(assertDoesNotThrow(() -> controller.execute(workoutId)));
  }
}
