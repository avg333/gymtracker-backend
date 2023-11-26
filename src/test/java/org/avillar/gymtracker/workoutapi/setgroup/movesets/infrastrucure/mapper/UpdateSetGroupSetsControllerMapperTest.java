package org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class UpdateSetGroupSetsControllerMapperTest {

  private final UpdateSetGroupSetsControllerMapper mapper =
      Mappers.getMapper(UpdateSetGroupSetsControllerMapper.class);

  private static void validateSet(final UpdateSetGroupSetsResponse result, final Set expected) {
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
  }

  @Test
  void shouldMapSetListToUpdateSetGroupSetsResponseList() {
    final List<Set> source = Instancio.createList(Set.class);
    source.add(null);

    final List<UpdateSetGroupSetsResponse> expected = mapper.map(source);
    assertThat(expected).isNotNull().hasSize(source.size());

    for (int i = 0; i < source.size(); i++) {
      validateSet(expected.get(i), source.get(i));
    }
  }

  @Test
  void shouldReturnNullWhenSetListIsNull() {
    assertNull(mapper.map(null));
  }
}
