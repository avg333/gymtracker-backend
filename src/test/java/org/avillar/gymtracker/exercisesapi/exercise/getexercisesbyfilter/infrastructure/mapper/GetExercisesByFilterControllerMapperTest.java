package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class GetExercisesByFilterControllerMapperTest {

  private final GetExercisesByFilterControllerMapper mapper =
      Mappers.getMapper(GetExercisesByFilterControllerMapper.class);

  @Test
  void shouldMapExerciseListToGetExercisesByFilterResponseListSuccessfully() {
    final List<Exercise> source = Instancio.createList(Exercise.class);

    final List<GetExercisesByFilterResponse> result = mapper.map(source);
    assertThat(result).isNotNull().hasSize(source.size());

    for (int i = 0; i < source.size(); i++) {
      assertThat(result.get(i).id()).isEqualTo(source.get(i).getId());
      assertThat(result.get(i).name()).isEqualTo(source.get(i).getName());
      assertThat(result.get(i).description()).isEqualTo(source.get(i).getDescription());
      assertThat(result.get(i).unilateral()).isEqualTo(source.get(i).getUnilateral());
    }
  }

  @Test
  void shouldReturnNullWhenExerciseListIsNull() {
    final List<Exercise> exercises = null;
    assertNull(mapper.map(exercises));
  }
}
