package org.avillar.gymtracker.workoutapi.workout.getworkoutdateandid.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.application.GetWorkoutsDateAndIdService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.GetWorkoutsDateAndIdControllerImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.model.GetWorkoutsDateAndIdResponse;
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
class GetWorkoutsDateAndIdControllerImplTest {

  private static final int LIST_SIZE = 5;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetWorkoutsDateAndIdControllerImpl getWorkoutsDateAndIdControllerImpl;

  @Mock private GetWorkoutsDateAndIdService getWorkoutsDateAndIdService;

  @Test
  void getWorkoutsIdAndDateByUserAndExercise() {
    final Map<Date, UUID> expected = new HashMap<>();
    for (int i = 0; i < LIST_SIZE; i++) {
      expected.put(easyRandom.nextObject(Date.class), UUID.randomUUID());
    }
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();

    when(getWorkoutsDateAndIdService.execute(userId, exerciseId)).thenReturn(expected);

    final GetWorkoutsDateAndIdResponse result =
        getWorkoutsDateAndIdControllerImpl.execute(userId, exerciseId);
    assertThat(result.getWorkoutsDateAndId()).hasSameSizeAs(expected);
    assertThat(result.getWorkoutsDateAndId()).usingRecursiveComparison().isEqualTo(expected);
  }
}
