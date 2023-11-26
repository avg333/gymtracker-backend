package org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataRequest;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponse;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataRequestDto;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataResponseDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class UpdateSetDataControllerMapperTest {

  private final UpdateSetDataControllerMapper mapper =
      Mappers.getMapper(UpdateSetDataControllerMapper.class);

  @Test
  void shouldMapUpdateSetDataResponseToUpdateSetDataResponseDto() {
    final UpdateSetDataResponse updateSetDataResponse =
        Instancio.create(UpdateSetDataResponse.class);

    final UpdateSetDataResponseDto updateSetDataResponseDto = mapper.map(updateSetDataResponse);
    assertThat(updateSetDataResponseDto).isNotNull();
    assertThat(updateSetDataResponseDto.getDescription())
        .isEqualTo(updateSetDataResponse.getDescription());
    assertThat(updateSetDataResponseDto.getReps()).isEqualTo(updateSetDataResponse.getReps());
    assertThat(updateSetDataResponseDto.getRir()).isEqualTo(updateSetDataResponse.getRir());
    assertThat(updateSetDataResponseDto.getWeight()).isEqualTo(updateSetDataResponse.getWeight());
    assertThat(updateSetDataResponseDto.getCompletedAt())
        .isEqualTo(updateSetDataResponse.getCompletedAt());
  }

  @Test
  void shouldReturnNullWhenUpdateSetDataResponseIsNull() {
    final UpdateSetDataResponse updateSetDataResponse = null;
    assertThat(mapper.map(updateSetDataResponse)).isNull();
  }

  @Test
  void shouldMapUpdateSetDataRequestDtoToUpdateSetDataRequest() {
    final UpdateSetDataRequestDto updateSetDataRequestDto =
        Instancio.create(UpdateSetDataRequestDto.class);

    final UpdateSetDataRequest updateSetDataRequest = mapper.map(updateSetDataRequestDto);
    assertThat(updateSetDataRequest).isNotNull();
    assertThat(updateSetDataRequest.getDescription())
        .isEqualTo(updateSetDataRequestDto.getDescription());
    assertThat(updateSetDataRequest.getReps()).isEqualTo(updateSetDataRequestDto.getReps());
    assertThat(updateSetDataRequest.getRir()).isEqualTo(updateSetDataRequestDto.getRir());
    assertThat(updateSetDataRequest.getWeight()).isEqualTo(updateSetDataRequestDto.getWeight());
    assertThat(updateSetDataRequest.getCompleted())
        .isEqualTo(updateSetDataRequestDto.getCompleted());
  }

  @Test
  void shouldReturnNullWhenUpdateSetDataRequestDtoIsNull() {
    final UpdateSetDataRequestDto updateSetDataRequestDto = null;
    assertThat(mapper.map(updateSetDataRequestDto)).isNull();
  }
}
