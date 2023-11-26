package org.avillar.gymtracker.workoutapi.set.updatesetdata.application.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class UpdateSetDataServiceMapperTest {

  private final UpdateSetDataServiceMapper mapper =
      Mappers.getMapper(UpdateSetDataServiceMapper.class);

  @Test
  void shouldMapSetToUpdateSetDataResponse() {
    final Set set = Instancio.create(Set.class);

    final UpdateSetDataResponse updateSetDataResponse = mapper.map(set);
    assertThat(updateSetDataResponse).isNotNull();
    assertThat(updateSetDataResponse.getDescription()).isEqualTo(set.getDescription());
    assertThat(updateSetDataResponse.getReps()).isEqualTo(set.getReps());
    assertThat(updateSetDataResponse.getRir()).isEqualTo(set.getRir());
    assertThat(updateSetDataResponse.getWeight()).isEqualTo(set.getWeight());
    assertThat(updateSetDataResponse.getCompletedAt()).isEqualTo(set.getCompletedAt());
  }

  @Test
  void shouldReturnNullWhenSetIsNull() {
    final Set set = null;
    assertThat(mapper.map(set)).isNull();
  }
}
