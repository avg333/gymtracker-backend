package org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.application.DeleteSetGroupService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteGetSetExerciseSetGroupsControllerTest {

  @InjectMocks private DeleteSetGroupControllerImpl deleteSetGroupControllerImpl;

  @Mock private DeleteSetGroupService deleteSetGroupService;

  @Test
  void deleteSetGroup() {
    final UUID setGroupId = UUID.randomUUID();

    Mockito.doNothing().when(deleteSetGroupService).execute(setGroupId);

    Assertions.assertDoesNotThrow(() -> deleteSetGroupControllerImpl.delete(setGroupId));
  }
}
