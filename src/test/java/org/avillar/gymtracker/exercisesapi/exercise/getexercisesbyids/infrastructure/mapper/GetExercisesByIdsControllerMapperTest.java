package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroupExercise;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.model.GetExercisesByIdsResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class GetExercisesByIdsControllerMapperTest {

  private final GetExercisesByIdsControllerMapper mapper =
      Mappers.getMapper(GetExercisesByIdsControllerMapper.class);

  @Test
  void shouldMapExerciseToGetExerciseByIdResponseResponse() {
    final List<Exercise> source = Instancio.createList(Exercise.class);

    final List<GetExercisesByIdsResponse> result = mapper.map(source);
    assertThat(result).isNotNull().hasSize(source.size());

    for (int i = 0; i < source.size(); i++) {
      checkExercise(result.get(i), source.get(i));
    }
  }

  private void checkExercise(GetExercisesByIdsResponse result, Exercise source) {
    if (source == null) {
      assertThat(result).isNull();
      return;
    }

    assertThat(result.id()).isEqualTo(source.getId());
    assertThat(result.name()).isEqualTo(source.getName());
    assertThat(result.description()).isEqualTo(source.getDescription());
    assertThat(result.unilateral()).isEqualTo(source.getUnilateral());

    if (source.getLoadType() != null) {
      checkLoadType(result.loadType(), source.getLoadType());
    } else {
      assertThat(result.loadType()).isNull();
    }

    if (source.getMuscleSubGroups() != null) {
      checkMuscleSubGroups(result, source);
    } else {
      assertThat(result.muscleSubGroups()).isNull();
    }

    if (source.getMuscleGroupExercises() != null) {
      checkMuscleGroups(result, source);
    } else {
      assertThat(result.muscleGroups()).isNull();
    }
  }

  void checkLoadType(GetExercisesByIdsResponse.LoadType result, LoadType source) {
    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(source.getId());
    assertThat(result.name()).isEqualTo(source.getName());
    assertThat(result.description()).isEqualTo(source.getDescription());
  }

  private void checkMuscleSubGroups(GetExercisesByIdsResponse result, Exercise source) {
    assertThat(result.muscleSubGroups()).isNotNull().hasSize(source.getMuscleSubGroups().size());
    for (int i = 0; i < source.getMuscleSubGroups().size(); i++) {
      checkMuscleSubGroup(result.muscleSubGroups().get(i), source.getMuscleSubGroups().get(i));
    }
  }

  void checkMuscleSubGroup(GetExercisesByIdsResponse.MuscleSubGroup result, MuscleSubGroup source) {
    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(source.getId());
    assertThat(result.name()).isEqualTo(source.getName());
    assertThat(result.description()).isEqualTo(source.getDescription());
  }

  private void checkMuscleGroups(GetExercisesByIdsResponse result, Exercise source) {
    assertThat(result.muscleGroups()).isNotNull().hasSize(source.getMuscleGroupExercises().size());
    for (int i = 0; i < source.getMuscleGroupExercises().size(); i++) {
      checkMuscleGroup(result.muscleGroups().get(i), source.getMuscleGroupExercises().get(i));
    }
  }

  void checkMuscleGroup(GetExercisesByIdsResponse.MuscleGroup result, MuscleGroupExercise source) {
    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(source.getId());
    assertThat(result.weight()).isEqualTo(source.getWeight());

    if (source.getMuscleGroup() != null) {
      assertThat(result.name()).isEqualTo(source.getMuscleGroup().getName());
      assertThat(result.description()).isEqualTo(source.getMuscleGroup().getDescription());
    } else {
      assertThat(result.name()).isNull();
      assertThat(result.description()).isNull();
    }
  }

  @Test
  void shouldReturnNullWhenExerciseListIsNull() {
    final List<Exercise> source = null;
    assertThat(mapper.map(source)).isNull();
  }

  @Test
  void shouldReturnNullWhenExerciseIsNull() {
    final Exercise source = null;
    assertThat(mapper.map(source)).isNull();
  }
}
