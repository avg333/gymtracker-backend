package org.avillar.gymtracker.usersapi.common.facade.settings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.usersapi.common.adapter.repository.SettingsDao;
import org.avillar.gymtracker.usersapi.common.adapter.repository.model.SettingsEntity;
import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsNotFoundException;
import org.avillar.gymtracker.usersapi.common.facade.settings.mapper.SettingsFacadeMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class SettingsFacadeImplTest {

  @InjectMocks private SettingsFacadeImpl settingsFacadeImpl;

  @Mock private SettingsDao settingsDao;
  @Mock private SettingsFacadeMapper settingsFacadeMapper;

  @Test
  void shouldGetSettingsByUserIdSuccessfully() throws SettingsNotFoundException {
    final SettingsEntity settingsEntity = Instancio.create(SettingsEntity.class);
    final Settings settings = Instancio.create(Settings.class);

    when(settingsDao.findByUserId(settings.getUserId())).thenReturn(Optional.of(settingsEntity));
    when(settingsFacadeMapper.map(settingsEntity)).thenReturn(settings);

    assertThat(settingsFacadeImpl.getSettingsByUserId(settings.getUserId())).isEqualTo(settings);
  }

  @Test
  void shouldThrowSettingsNotFoundExceptionWhenSettingsByUserIdIsNotFound() {
    final UUID userId = Instancio.create(UUID.class);
    final SettingsNotFoundException ex = new SettingsNotFoundException(userId);

    when(settingsDao.findByUserId(userId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> settingsFacadeImpl.getSettingsByUserId(userId)).isEqualTo(ex);
  }

  @Test
  void shouldSaveSettingsSuccessfully() {
    final Settings settingsBeforeSave = Instancio.create(Settings.class);
    final SettingsEntity settingsEntityBeforeSave = Instancio.create(SettingsEntity.class);
    final SettingsEntity settingsEntityAfterSave = Instancio.create(SettingsEntity.class);
    final Settings settingsAfterSave = Instancio.create(Settings.class);

    when(settingsFacadeMapper.map(settingsBeforeSave)).thenReturn(settingsEntityBeforeSave);
    when(settingsDao.save(settingsEntityBeforeSave)).thenReturn(settingsEntityAfterSave);
    when(settingsFacadeMapper.map(settingsEntityAfterSave)).thenReturn(settingsAfterSave);

    assertThat(settingsFacadeImpl.saveSettings(settingsBeforeSave)).isEqualTo(settingsAfterSave);
  }
}
