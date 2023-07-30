package org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.infrastructure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.application.DeleteSetGroupService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class DeleteSetGroupControllerTest {

  @InjectMocks private DeleteSetGroupControllerImpl deleteSetGroupControllerImpl;

  @Mock private DeleteSetGroupService deleteSetGroupService;

  @Test
  void deleteSetGroup() {
    final UUID setGroupId = UUID.randomUUID();

    doNothing().when(deleteSetGroupService).execute(setGroupId);

    assertNull(assertDoesNotThrow(() -> deleteSetGroupControllerImpl.execute(setGroupId)));
  }
}
