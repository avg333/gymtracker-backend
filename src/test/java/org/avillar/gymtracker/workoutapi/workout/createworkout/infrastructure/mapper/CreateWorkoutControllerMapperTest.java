package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutRequest;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class CreateWorkoutControllerMapperTest {

  private final CreateWorkoutControllerMapper mapper =
      Mappers.getMapper(CreateWorkoutControllerMapper.class);

  @Test
  void shouldMapCreateWorkoutResponseToCreateWorkoutResponseDto() {
    final Workout workout = Instancio.create(Workout.class);

    final CreateWorkoutResponse createWorkoutResponse = mapper.map(workout);
    assertThat(createWorkoutResponse).isNotNull();
    assertThat(createWorkoutResponse.id()).isEqualTo(workout.getId());
    assertThat(createWorkoutResponse.date()).isEqualTo(workout.getDate());
    assertThat(createWorkoutResponse.description()).isEqualTo(workout.getDescription());
    assertThat(createWorkoutResponse.userId()).isEqualTo(workout.getUserId());
  }

  @Test
  void shouldReturnNullWhenCreateWorkoutResponseIsNull() {
    final Workout createWorkoutResponse = null;
    assertThat(mapper.map(createWorkoutResponse)).isNull();
  }

  @Test
  void shouldMapCreateWorkoutRequestDtoToCreateWorkoutRequest() {
    final CreateWorkoutRequest createWorkoutRequest = Instancio.create(CreateWorkoutRequest.class);

    final Workout workout = mapper.map(createWorkoutRequest);
    assertThat(workout).isNotNull();
    assertThat(workout.getDate()).isEqualTo(createWorkoutRequest.date());
    assertThat(workout.getDescription()).isEqualTo(createWorkoutRequest.description());
  }

  @Test
  void shouldReturnNullWhenCreateWorkoutRequestDtoIsNull() {
    final CreateWorkoutRequest createWorkoutRequest = null;
    assertThat(mapper.map(createWorkoutRequest)).isNull();
  }
}
