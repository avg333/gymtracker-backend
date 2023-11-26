package org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.model.GetWorkoutResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class GetWorkoutControllerMapperTest {
  private final GetWorkoutControllerMapper mapper =
      Mappers.getMapper(GetWorkoutControllerMapper.class);

  @Test
  void shouldMapWorkoutToGetWorkoutResponse() {
    final Workout getWorkoutResponse = Instancio.create(Workout.class);

    final GetWorkoutResponse getWorkoutResponseDto = mapper.map(getWorkoutResponse);
    assertThat(getWorkoutResponseDto).isNotNull();
    assertThat(getWorkoutResponseDto.id()).isEqualTo(getWorkoutResponse.getId());
    assertThat(getWorkoutResponseDto.date()).isEqualTo(getWorkoutResponse.getDate());
    assertThat(getWorkoutResponseDto.description()).isEqualTo(getWorkoutResponse.getDescription());
    assertThat(getWorkoutResponseDto.userId()).isEqualTo(getWorkoutResponse.getUserId());
  }

  @Test
  void shouldReturnNullWhenWorkoutIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
