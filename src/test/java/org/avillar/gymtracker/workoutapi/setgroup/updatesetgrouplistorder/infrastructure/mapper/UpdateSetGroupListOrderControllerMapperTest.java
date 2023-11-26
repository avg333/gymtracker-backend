package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class UpdateSetGroupListOrderControllerMapperTest {

  private final UpdateSetGroupListOrderControllerMapper mapper =
      Mappers.getMapper(UpdateSetGroupListOrderControllerMapper.class);

  private static void validateSetGroup(
      final UpdateSetGroupListOrderResponse result, final SetGroup expected) {
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
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldMapSetGroupListToUpdateSetGroupListOrderResponseList(final boolean hasWorkout) {
    final List<SetGroup> setGroups = Instancio.createList(SetGroup.class);
    if (!hasWorkout) {
      setGroups.forEach(setGroup -> setGroup.setWorkout(null));
    }
    setGroups.add(null);

    final List<UpdateSetGroupListOrderResponse> result = mapper.map(setGroups);
    assertThat(result).isNotNull().hasSize(setGroups.size());

    for (int i = 0; i < setGroups.size(); i++) {
      validateSetGroup(result.get(i), setGroups.get(i));
    }
  }

  @Test
  void shouldReturnNullWhenSetGroupListIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
