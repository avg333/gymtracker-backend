package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroupExercise;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponseDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class GetWorkoutDetailsControllerMapperTest {

  private final GetWorkoutDetailsControllerMapper mapper =
      Mappers.getMapper(GetWorkoutDetailsControllerMapper.class);

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldMapWorkoutToGetWorkoutDetailsResponse(final boolean hasSetGroups) {
    final Workout source = Instancio.create(Workout.class);
    source.getSetGroups().add(null);
    source.getSetGroups().get(0).getSets().add(null);
    source.getSetGroups().get(0).getExercise().getMuscleGroupExercises().add(null);
    source.getSetGroups().get(0).getExercise().getMuscleSubGroups().add(null);

    source.getSetGroups().get(1).setSets(null);
    source.getSetGroups().get(1).getExercise().setLoadType(null);
    source.getSetGroups().get(1).getExercise().setMuscleGroupExercises(null);
    source.getSetGroups().get(1).getExercise().setMuscleSubGroups(null);

    source.getSetGroups().add(SetGroup.builder().build());

    if (!hasSetGroups) {
      source.setSetGroups(null);
    }

    final GetWorkoutDetailsResponseDto result = mapper.map(source);
    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(source.getId());
    assertThat(result.date()).isEqualTo(source.getDate());
    assertThat(result.description()).isEqualTo(source.getDescription());
    assertThat(result.userId()).isEqualTo(source.getUserId());

    if (source.getSetGroups() == null) {
      assertThat(result.setGroups()).isNull();
      return;
    }

    assertThat(result.setGroups()).hasSameSizeAs(source.getSetGroups());
    for (int i = 0; i < result.setGroups().size(); i++) {
      validateSetGroup(source.getSetGroups().get(i), result.setGroups().get(i));
    }
  }

  private void validateSetGroup(
      final SetGroup source, final GetWorkoutDetailsResponseDto.SetGroup result) {
    if (source == null) {
      assertThat(result).isNull();
      return;
    }

    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(source.getId());
    assertThat(result.listOrder()).isEqualTo(source.getListOrder());
    assertThat(result.description()).isEqualTo(source.getDescription());
    assertThat(result.exerciseId()).isEqualTo(source.getExerciseId());

    if (source.getExercise() != null) {
      validateExercise(source.getExercise(), result.exercise());
    } else {
      assertThat(result.exercise()).isNull();
    }

    if (source.getSets() == null) {
      assertThat(result.sets()).isNull();
      return;
    }

    assertThat(result.sets()).hasSameSizeAs(source.getSets());
    for (int i = 0; i < result.sets().size(); i++) {
      validateSet(source.getSets().get(i), result.sets().get(i));
    }
  }

  private void validateSet(
      final Set source, final GetWorkoutDetailsResponseDto.SetGroup.Set result) {
    if (source == null) {
      assertThat(result).isNull();
      return;
    }

    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(source.getId());
    assertThat(result.listOrder()).isEqualTo(source.getListOrder());
    assertThat(result.description()).isEqualTo(source.getDescription());
    assertThat(result.reps()).isEqualTo(source.getReps());
    assertThat(result.rir()).isEqualTo(source.getRir());
    assertThat(result.weight()).isEqualTo(source.getWeight());
    assertThat(result.completedAt()).isEqualTo(source.getCompletedAt());
  }

  private void validateExercise(
      final Exercise source, final GetWorkoutDetailsResponseDto.SetGroup.Exercise result) {
    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(source.getId());
    assertThat(result.name()).isEqualTo(source.getName());
    assertThat(result.description()).isEqualTo(source.getDescription());
    assertThat(result.unilateral()).isEqualTo(source.getUnilateral());
    validateLoadType(source.getLoadType(), result.loadType());

    if (source.getMuscleSubGroups() != null) {
      assertThat(result.muscleSubGroups()).hasSameSizeAs(source.getMuscleSubGroups());
      for (int i = 0; i < result.muscleSubGroups().size(); i++) {
        validateMuscleSubGroup(source.getMuscleSubGroups().get(i), result.muscleSubGroups().get(i));
      }
    } else {
      assertThat(result.muscleSubGroups()).isNull();
    }

    if (source.getMuscleGroupExercises() != null) {
      assertThat(result.muscleGroups()).hasSameSizeAs(source.getMuscleGroupExercises());
      for (int i = 0; i < result.muscleGroups().size(); i++) {
        validateMuscleGroup(source.getMuscleGroupExercises().get(i), result.muscleGroups().get(i));
      }
    } else {
      assertThat(result.muscleGroups()).isNull();
    }
  }

  private void validateLoadType(
      final LoadType source, final GetWorkoutDetailsResponseDto.SetGroup.Exercise.LoadType result) {
    if (source == null) {
      assertThat(result).isNull();
      return;
    }

    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(source.getId());
    assertThat(result.name()).isEqualTo(source.getName());
    assertThat(result.description()).isEqualTo(source.getDescription());
  }

  private void validateMuscleSubGroup(
      final MuscleSubGroup source,
      final GetWorkoutDetailsResponseDto.SetGroup.Exercise.MuscleSubGroup result) {
    if (source == null) {
      assertThat(result).isNull();
      return;
    }

    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(source.getId());
    assertThat(result.name()).isEqualTo(source.getName());
    assertThat(result.description()).isEqualTo(source.getDescription());
  }

  private void validateMuscleGroup(
      final MuscleGroupExercise source,
      final GetWorkoutDetailsResponseDto.SetGroup.Exercise.MuscleGroup result) {
    if (source == null || source.getMuscleGroup() == null) {
      assertThat(result).isNull();
      return;
    }

    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(source.getMuscleGroup().getId());
    assertThat(result.name()).isEqualTo(source.getMuscleGroup().getName());
    assertThat(result.description()).isEqualTo(source.getMuscleGroup().getDescription());
    assertThat(result.weight()).isEqualTo(source.getWeight());
  }

  @Test
  void shouldReturnNullWhenWorkoutIsNull() {
    final Workout source = null;
    assertThat(mapper.map(source)).isNull();
  }
}
