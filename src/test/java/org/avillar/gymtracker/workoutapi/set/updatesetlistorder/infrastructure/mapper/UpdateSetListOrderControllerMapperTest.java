package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class UpdateSetListOrderControllerMapperTest {

  private final UpdateSetListOrderControllerMapper mapper =
      Mappers.getMapper(UpdateSetListOrderControllerMapper.class);

  private static void checkSet(final UpdateSetListOrderResponse result, final Set expected) {
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

    if (expected.getSetGroup() != null) {
      assertThat(result.setGroup()).isNotNull();
      assertThat(result.setGroup().id()).isEqualTo(expected.getSetGroup().getId());
    } else {
      assertThat(result.setGroup()).isNull();
    }
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldMapSetListToUpdateSetListOrderResponseList(final boolean hasSetGroup) {
    final List<Set> sets = Instancio.createList(Set.class);
    if (!hasSetGroup) {
      sets.forEach(set -> set.setSetGroup(null));
    }
    sets.add(null);

    final List<UpdateSetListOrderResponse> updateSetListOrderResponse = mapper.map(sets);
    assertThat(updateSetListOrderResponse).isNotNull().hasSize(sets.size());

    for (int i = 0; i < sets.size(); i++) {
      checkSet(updateSetListOrderResponse.get(i), sets.get(i));
    }
  }

  @Test
  void shouldReturnNullWhenSetListIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
