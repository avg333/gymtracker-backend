package org.avillar.gymtracker.usersapi.common.facade.settings.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.usersapi.common.adapter.repository.model.SettingsEntity;
import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class SettingsFacadeMapperTest {

  private final SettingsFacadeMapper mapper = Mappers.getMapper(SettingsFacadeMapper.class);

  @Test
  void shouldMapSettingsEntityToSettings() {
    final SettingsEntity source = Instancio.create(SettingsEntity.class);

    final Settings result = mapper.map(source);
    assertThat(result).isNotNull();
    assertThat(result.getInternationalSystem()).isEqualTo(source.getInternationalSystem());
    assertThat(result.getSelectedIncrement()).isEqualTo(source.getSelectedIncrement());
    assertThat(result.getSelectedBar()).isEqualTo(source.getSelectedBar());
    if (source.getSelectedPlates() != null) {
      assertThat(result.getSelectedPlates()).containsExactlyElementsOf(source.getSelectedPlates());
    } else {
      assertThat(result.getSelectedPlates()).isNull();
    }
  }

  @Test
  void shouldReturnNullWhenSettingsEntityIsNull() {
    final SettingsEntity source = null;
    assertThat(mapper.map(source)).isNull();
  }

  @Test
  void shouldMapSettingsToSettingsEntity() {
    final Settings source = Instancio.create(Settings.class);

    final SettingsEntity result = mapper.map(source);
    assertThat(result).isNotNull();
    assertThat(result.getInternationalSystem()).isEqualTo(source.getInternationalSystem());
    assertThat(result.getSelectedIncrement()).isEqualTo(source.getSelectedIncrement());
    assertThat(result.getSelectedBar()).isEqualTo(source.getSelectedBar());
    if (source.getSelectedPlates() != null) {
      assertThat(result.getSelectedPlates()).containsExactlyElementsOf(source.getSelectedPlates());
    } else {
      assertThat(result.getSelectedPlates()).isNull();
    }
  }

  @Test
  void shouldReturnNullWhenSettingsIsNull() {
    final Settings source = null;
    assertThat(mapper.map(source)).isNull();
  }
}
