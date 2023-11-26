package org.avillar.gymtracker.usersapi.common.facade.settings;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.usersapi.common.adapter.repository.SettingsDao;
import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsNotFoundException;
import org.avillar.gymtracker.usersapi.common.facade.settings.mapper.SettingsFacadeMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SettingsFacadeImpl implements SettingsFacade {

  private final SettingsDao settingsDao;
  private final SettingsFacadeMapper settingsFacadeMapper;

  @Override
  public Settings getSettingsByUserId(final UUID userId) throws SettingsNotFoundException {
    final Settings settings =
        settingsFacadeMapper.map(
            settingsDao
                .findByUserId(userId)
                .orElseThrow(() -> new SettingsNotFoundException(userId)));

    if (settings != null) {
      Optional.ofNullable(settings.getSelectedPlates())
          .ifPresent(selectedPlates -> selectedPlates.sort(Double::compareTo));
    }

    return settings;
  }

  @Override
  public Settings saveSettings(final Settings settings) {
    return settingsFacadeMapper.map(settingsDao.save(settingsFacadeMapper.map(settings)));
  }
}
