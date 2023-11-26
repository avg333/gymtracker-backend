package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.model.GetSetGroupsByExerciseResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class GetSetGroupsByExerciseControllerMapperTest {

  private final GetSetGroupsByExerciseControllerMapper mapper =
      Mappers.getMapper(GetSetGroupsByExerciseControllerMapper.class);

  private static void validateSetGroup(
      final GetSetGroupsByExerciseResponse result, final SetGroup expected) {
    if (expected == null) {
      assertThat(result).isNull();
      return;
    }

    assertThat(result.id()).isEqualTo(expected.getId());
    assertThat(result.listOrder()).isEqualTo(expected.getListOrder());
    assertThat(result.description()).isEqualTo(expected.getDescription());
    assertThat(result.exerciseId()).isEqualTo(expected.getExerciseId());

    if (expected.getWorkout() != null) {
      assertThat(result.workout()).isNotNull();
      assertThat(result.workout().id()).isEqualTo(expected.getWorkout().getId());
    } else {
      assertThat(result.workout()).isNull();
    }

    if (expected.getSets() == null) {
      assertThat(result.sets()).isNull();
    } else {
      assertThat(result.sets()).isNotNull().hasSize(expected.getSets().size());
      for (int i = 0; i < result.sets().size(); i++) {
        validateSet(result.sets().get(i), expected.getSets().get(i));
      }
    }
  }

  private static void validateSet(
      final GetSetGroupsByExerciseResponse.Set result, final Set expected) {
    if (expected == null) {
      assertThat(result).isNull();
      return;
    }

    assertThat(result.id()).isEqualTo(expected.getId());
    assertThat(result.listOrder()).isEqualTo(expected.getListOrder());
    assertThat(result.description()).isEqualTo(expected.getDescription());
    assertThat(result.reps()).isEqualTo(expected.getReps());
    assertThat(result.rir()).isEqualTo(expected.getRir());
    assertThat(result.weight()).isEqualTo(expected.getWeight());
    assertThat(result.completedAt()).isEqualTo(expected.getCompletedAt());
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldMapSetGroupListToGetSetGroupsByExerciseResponseList(
      final boolean hasWorkoutButHasNotSets) {
    final List<SetGroup> setGroups = Instancio.createList(SetGroup.class);
    if (!hasWorkoutButHasNotSets) {
      setGroups.forEach(setGroup -> setGroup.setWorkout(null));
      setGroups.get(0).getSets().add(null);
      setGroups.add(null);
    } else {
      setGroups.forEach(setGroup -> setGroup.setSets(null));
    }

    final List<GetSetGroupsByExerciseResponse> getSetGroupsByExerciseResponseList =
        mapper.map(setGroups);
    assertThat(getSetGroupsByExerciseResponseList).isNotNull().hasSize(setGroups.size());

    for (int i = 0; i < getSetGroupsByExerciseResponseList.size(); i++) {
      validateSetGroup(getSetGroupsByExerciseResponseList.get(i), setGroups.get(i));
    }
  }

  @Test
  void shouldReturnNullWhenSetGroupListIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
