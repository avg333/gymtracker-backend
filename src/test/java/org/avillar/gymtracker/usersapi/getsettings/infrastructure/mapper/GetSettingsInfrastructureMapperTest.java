package org.avillar.gymtracker.usersapi.getsettings.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.avillar.gymtracker.usersapi.getsettings.infrastructure.model.GetSettingsResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class GetSettingsInfrastructureMapperTest {

  private final GetSettingsInfrastructureMapper mapper =
      Mappers.getMapper(GetSettingsInfrastructureMapper.class);

  @Test
  void shouldMapGetUserSettingsResponseToGetUserSettingsResponseDto() {
    final Settings source = Instancio.create(Settings.class);

    final GetSettingsResponse result = mapper.map(source);
    assertThat(result).isNotNull();
    assertThat(result.internationalSystem()).isEqualTo(source.getInternationalSystem());
    assertThat(result.selectedIncrement()).isEqualTo(source.getSelectedIncrement());
    assertThat(result.selectedBar()).isEqualTo(source.getSelectedBar());
    if (source.getSelectedPlates() != null) {
      assertThat(result.selectedPlates())
          .hasSize(source.getSelectedPlates().size())
          .usingRecursiveComparison()
          .isEqualTo(source.getSelectedPlates());
    } else {
      assertThat(result.selectedPlates()).isNull();
    }
  }

  @Test
  void shouldReturnNullWhenGetUserSettingsResponseIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
