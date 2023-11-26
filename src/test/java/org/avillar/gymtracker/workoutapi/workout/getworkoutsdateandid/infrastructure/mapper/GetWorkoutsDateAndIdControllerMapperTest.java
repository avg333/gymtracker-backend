package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.model.GetWorkoutsDateAndIdResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
class GetWorkoutsDateAndIdControllerMapperTest {

  private final GetWorkoutsDateAndIdControllerMapper mapper =
      new GetWorkoutsDateAndIdControllerMapper();

  @Test
  void shouldMapGetWorkoutResponseToGetWorkoutResponseDtoSuccessfully() {
    final Map<LocalDate, UUID> workoutsDateAndId = Instancio.createMap(LocalDate.class, UUID.class);

    final GetWorkoutsDateAndIdResponse expected = mapper.map(workoutsDateAndId);
    assertThat(expected).isNotNull();
    assertThat(expected.getWorkoutsDateAndId()).isEqualTo(workoutsDateAndId);
  }

  @Test
  void shouldReturnNullWhenGetWorkoutsDateAndIdResponseIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
