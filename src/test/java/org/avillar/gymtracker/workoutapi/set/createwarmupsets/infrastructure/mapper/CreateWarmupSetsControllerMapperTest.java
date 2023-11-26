package org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure.model.CreateWarmupSetsResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class CreateWarmupSetsControllerMapperTest {

  private final CreateWarmupSetsControllerMapper mapper =
      Mappers.getMapper(CreateWarmupSetsControllerMapper.class);

  private static void validateSet(List<Set> source, int i, List<CreateWarmupSetsResponse> result) {
    if (source.get(i) == null) {
      assertThat(result.get(i)).isNull();
      return;
    }

    assertThat(result.get(i)).isNotNull();
    assertThat(result.get(i).id()).isEqualTo(source.get(i).getId());
    assertThat(result.get(i).listOrder()).isEqualTo(source.get(i).getListOrder());
    assertThat(result.get(i).description()).isEqualTo(source.get(i).getDescription());
    assertThat(result.get(i).reps()).isEqualTo(source.get(i).getReps());
    assertThat(result.get(i).rir()).isEqualTo(source.get(i).getRir());
    assertThat(result.get(i).weight()).isEqualTo(source.get(i).getWeight());
  }

  @Test
  void shouldMapSetListToCreateWarmupSetsResponseList() {
    final List<Set> source = Instancio.createList(Set.class);
    source.add(null);

    final List<CreateWarmupSetsResponse> result = mapper.map(source);
    assertThat(result).isNotNull().hasSize(source.size());

    for (int i = 0; i < source.size(); i++) {
      validateSet(source, i, result);
    }
  }

  @Test
  void shouldReturnNullWhenSetListIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
