package org.avillar.gymtracker.workoutapi.set.deleteset.infrastructure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.deleteset.application.DeleteSetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class DeleteSetControllerImplTest {

  @InjectMocks private DeleteSetControllerImpl controller;

  @Mock private DeleteSetService service;

  @Test
  void shouldDeleteSetSuccessfully() throws SetNotFoundException, WorkoutIllegalAccessException {
    final UUID setId = UUID.randomUUID();

    doNothing().when(service).execute(setId);

    assertNull(assertDoesNotThrow(() -> controller.execute(setId)));
  }
}
