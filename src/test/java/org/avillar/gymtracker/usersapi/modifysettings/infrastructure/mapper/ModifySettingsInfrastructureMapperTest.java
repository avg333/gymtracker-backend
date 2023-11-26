package org.avillar.gymtracker.usersapi.modifysettings.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.avillar.gymtracker.usersapi.modifysettings.infrastructure.model.ModifySettingsRequestDto;
import org.avillar.gymtracker.usersapi.modifysettings.infrastructure.model.ModifySettingsResponseDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class ModifySettingsInfrastructureMapperTest {

  private final ModifySettingsInfrastructureMapper mapper =
      Mappers.getMapper(ModifySettingsInfrastructureMapper.class);

  @Test
  void shouldMapModifyUserSettingsRequestToSettings() {
    final ModifySettingsRequestDto source = Instancio.create(ModifySettingsRequestDto.class);

    final Settings result = mapper.map(source);
    assertThat(result).isNotNull();
    assertThat(result.getInternationalSystem()).isEqualTo(source.internationalSystem());
    assertThat(result.getSelectedIncrement()).isEqualTo(source.selectedIncrement());
    assertThat(result.getSelectedBar()).isEqualTo(source.selectedBar());
    if (source.selectedPlates() != null) {
      assertThat(result.getSelectedPlates())
          .hasSize(source.selectedPlates().size())
          .usingRecursiveComparison()
          .isEqualTo(source.selectedPlates());
    } else {
      assertThat(result.getSelectedPlates()).isNull();
    }
  }

  @Test
  void shouldReturnNullWhenModifyUserSettingsRequestIsNull() {
    final ModifySettingsRequestDto source = null;
    assertThat(mapper.map(source)).isNull();
  }

  @Test
  void shouldMapSettingsToModifyUserSettingsResponse() {
    final Settings source = Instancio.create(Settings.class);

    final ModifySettingsResponseDto result = mapper.map(source);
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
  void shouldReturnNullWhenSettingsIsNull() {
    final Settings source = null;
    assertThat(mapper.map(source)).isNull();
  }
}
