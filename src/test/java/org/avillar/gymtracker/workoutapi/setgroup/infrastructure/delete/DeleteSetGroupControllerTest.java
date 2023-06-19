package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.delete;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.delete.DeleteSetGroupService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteSetGroupControllerTest {

  @InjectMocks private DeleteSetGroupController deleteSetGroupController;

  @Mock private DeleteSetGroupService deleteSetGroupService;

  @Test
  void deleteSetGroup() {
    final UUID setGroupId = UUID.randomUUID();

    Mockito.doNothing().when(deleteSetGroupService).delete(setGroupId);

    Assertions.assertDoesNotThrow(() -> deleteSetGroupController.deleteSetGroup(setGroupId));
  }
}
