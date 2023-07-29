package org.avillar.gymtracker.workoutapi.set.deleteset.infrastructure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.deleteset.application.DeleteSetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class DeleteSetControllerImplTest {

  @InjectMocks private DeleteSetControllerImpl deleteSetControllerImpl;

  @Mock private DeleteSetService deleteSetService;

  @Test
  void deleteSet() {
    final UUID setId = UUID.randomUUID();

    doNothing().when(deleteSetService).execute(setId);

    final ResponseEntity<Void> response =
        assertDoesNotThrow(() -> deleteSetControllerImpl.execute(setId));
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertNull(response.getBody());
  }
}
