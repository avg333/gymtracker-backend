package org.avillar.gymtracker.usersapi.common.facade.settings;

import java.util.UUID;
import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsNotFoundException;

public interface SettingsFacade {

  Settings getSettingsByUserId(UUID userId) throws SettingsNotFoundException;

  Settings saveSettings(Settings settings);
}
