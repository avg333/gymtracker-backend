package org.avillar.gymtracker.workoutapi.set.getset.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.model.GetSetResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class GetSetControllerMapperTest {

  private final GetSetControllerMapper mapper = Mappers.getMapper(GetSetControllerMapper.class);

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldMapSetToGetSetResponse(final boolean hasSetGroup) {
    final Set set = Instancio.create(Set.class);
    if (!hasSetGroup) {
      set.setSetGroup(null);
    }

    final GetSetResponse getSetResponse = mapper.map(set);
    assertThat(getSetResponse).isNotNull();
    assertThat(getSetResponse.id()).isEqualTo(set.getId());
    assertThat(getSetResponse.listOrder()).isEqualTo(set.getListOrder());
    assertThat(getSetResponse.description()).isEqualTo(set.getDescription());
    assertThat(getSetResponse.reps()).isEqualTo(set.getReps());
    assertThat(getSetResponse.rir()).isEqualTo(set.getRir());
    assertThat(getSetResponse.weight()).isEqualTo(set.getWeight());
    assertThat(getSetResponse.completedAt()).isEqualTo(set.getCompletedAt());
    if (set.getSetGroup() != null) {
      assertThat(getSetResponse.setGroup().id()).isEqualTo(set.getSetGroup().getId());
    } else {
      assertThat(getSetResponse.setGroup()).isNull();
    }
  }

  @Test
  void shouldReturnNullWhenSetIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
