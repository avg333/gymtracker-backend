package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.application.GetWorkoutsDateAndIdService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.mapper.GetWorkoutsDateAndIdControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.model.GetWorkoutsDateAndIdResponse;
import org.instancio.Instancio;
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

  @InjectMocks private GetWorkoutsDateAndIdControllerImpl controller;

  @Mock private GetWorkoutsDateAndIdService service;
  @Mock private GetWorkoutsDateAndIdControllerMapper getWorkoutsDateAndIdControllerMapper;

  @Test
  void shouldGetWorkoutsIdAndDateSuccessfully() throws WorkoutIllegalAccessException {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final Map<LocalDate, UUID> response = Instancio.createMap(LocalDate.class, UUID.class);
    final GetWorkoutsDateAndIdResponse responseDto =
        Instancio.create(GetWorkoutsDateAndIdResponse.class);

    when(service.execute(userId, exerciseId)).thenReturn(response);
    when(getWorkoutsDateAndIdControllerMapper.map(response)).thenReturn(responseDto);

    assertThat(controller.execute(userId, exerciseId)).isEqualTo(responseDto);
  }
}
