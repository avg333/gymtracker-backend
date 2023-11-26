package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroupExercise;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.model.GetExerciseByIdResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class GetExerciseByIdControllerMapperTest {

  private final GetExerciseByIdControllerMapper mapper =
      Mappers.getMapper(GetExerciseByIdControllerMapper.class);

  @Test
  void shouldMapExerciseToGetExerciseByIdResponseResponse() {
    final Exercise source = Instancio.create(Exercise.class);

    final GetExerciseByIdResponse result = mapper.map(source);
    assertThat(result).isNotNull();
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

  void checkLoadType(GetExerciseByIdResponse.LoadType result, LoadType source) {
    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(source.getId());
    assertThat(result.name()).isEqualTo(source.getName());
    assertThat(result.description()).isEqualTo(source.getDescription());
  }

  private void checkMuscleSubGroups(GetExerciseByIdResponse result, Exercise source) {
    assertThat(result.muscleSubGroups()).isNotNull().hasSize(source.getMuscleSubGroups().size());
    for (int i = 0; i < source.getMuscleSubGroups().size(); i++) {
      checkMuscleSubGroup(result.muscleSubGroups().get(i), source.getMuscleSubGroups().get(i));
    }
  }

  void checkMuscleSubGroup(GetExerciseByIdResponse.MuscleSubGroup result, MuscleSubGroup source) {
    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(source.getId());
    assertThat(result.name()).isEqualTo(source.getName());
    assertThat(result.description()).isEqualTo(source.getDescription());
  }

  private void checkMuscleGroups(GetExerciseByIdResponse result, Exercise source) {
    assertThat(result.muscleGroups()).isNotNull().hasSize(source.getMuscleGroupExercises().size());
    for (int i = 0; i < source.getMuscleGroupExercises().size(); i++) {
      checkMuscleGroup(result.muscleGroups().get(i), source.getMuscleGroupExercises().get(i));
    }
  }

  void checkMuscleGroup(GetExerciseByIdResponse.MuscleGroup result, MuscleGroupExercise source) {
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
  void shouldReturnNullWhenExerciseIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
