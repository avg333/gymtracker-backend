package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.application.UpdateWorkoutDateService;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model.UpdateWorkoutDateRequest;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model.UpdateWorkoutDateResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class UpdateWorkoutDateControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UpdateWorkoutDateControllerImpl updateWorkoutDateControllerImpl;

  @Mock private UpdateWorkoutDateService updateWorkoutDateService;

  @Test
  void updateWorkoutDate() {
    final UUID workoutId = UUID.randomUUID();
    final UpdateWorkoutDateRequest updateWorkoutDateRequest =
        easyRandom.nextObject(UpdateWorkoutDateRequest.class);

    when(updateWorkoutDateService.execute(workoutId, updateWorkoutDateRequest.getDate()))
        .thenReturn(updateWorkoutDateRequest.getDate());

    final UpdateWorkoutDateResponse response =
        updateWorkoutDateControllerImpl.execute(workoutId, updateWorkoutDateRequest);
    assertNotNull(response);
    assertEquals(updateWorkoutDateRequest.getDate(), response.getDate());
  }
}
