package org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseRequest;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class CreteExerciseControllerMapperTest {

  private final CreteExerciseControllerMapper mapper =
      Mappers.getMapper(CreteExerciseControllerMapper.class);

  @Test
  void shouldMapCreateExerciseRequestToExercise() {
    final CreateExerciseRequest source = Instancio.create(CreateExerciseRequest.class);

    final Exercise target = mapper.map(source);
    assertThat(target).isNotNull();
    assertThat(target.getName()).isEqualTo(source.name());
    assertThat(target.getDescription()).isEqualTo(source.description());
    assertThat(target.getUnilateral()).isEqualTo(source.unilateral());
  }

  @Test
  void shouldMapExerciseToCreateExerciseResponse() {
    final Exercise source = Instancio.create(Exercise.class);

    final CreateExerciseResponse target = mapper.map(source);
    assertThat(target).isNotNull();
    assertThat(target.id()).isEqualTo(source.getId());
    assertThat(target.name()).isEqualTo(source.getName());
    assertThat(target.description()).isEqualTo(source.getDescription());
    assertThat(target.unilateral()).isEqualTo(source.getUnilateral());
  }

  @Test
  void shouldReturnNullWhenCreateExerciseRequestIsNull() {
    final CreateExerciseRequest createExerciseRequest = null;
    assertThat(mapper.map(createExerciseRequest)).isNull();
  }

  @Test
  void shouldReturnNullWhenExerciseIsNull() {
    final Exercise exercise = null;
    assertThat(mapper.map(exercise)).isNull();
  }
}
