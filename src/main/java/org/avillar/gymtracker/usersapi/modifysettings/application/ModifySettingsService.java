package org.avillar.gymtracker.usersapi.modifysettings.application;

import java.util.UUID;
import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsIllegalAccessException;

public interface ModifySettingsService {

  Settings execute(UUID userId, Settings settings) throws SettingsIllegalAccessException;
}
