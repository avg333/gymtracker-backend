package org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.model.GetNewSetDataResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class GetNewSetDataControllerMapperTest {

  private final GetNewSetDataControllerMapper mapper =
      Mappers.getMapper(GetNewSetDataControllerMapper.class);

  @Test
  void shouldMapSetToGetNewSetDataResponse() {
    final Set set = Instancio.create(Set.class);

    final GetNewSetDataResponse getNewSetDataResponse = mapper.map(set);
    assertThat(getNewSetDataResponse).isNotNull();
    assertThat(getNewSetDataResponse.description()).isEqualTo(set.getDescription());
    assertThat(getNewSetDataResponse.reps()).isEqualTo(set.getReps());
    assertThat(getNewSetDataResponse.rir()).isEqualTo(set.getRir());
    assertThat(getNewSetDataResponse.weight()).isEqualTo(set.getWeight());
  }

  @Test
  void shouldReturnNullWhenSetIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
