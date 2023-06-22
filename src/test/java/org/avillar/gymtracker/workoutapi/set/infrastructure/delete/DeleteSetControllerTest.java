package org.avillar.gymtracker.workoutapi.set.infrastructure.delete;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.delete.DeleteSetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteSetControllerTest {

  @InjectMocks private DeleteSetController deleteSetController;

  @Mock private DeleteSetService deleteSetService;

  @Test
  void deleteSet() {
    final UUID setId = UUID.randomUUID();

    Mockito.doNothing().when(deleteSetService).execute(setId);

    Assertions.assertDoesNotThrow(() -> deleteSetController.delete(setId));
  }
}
