package org.avillar.gymtracker.usersapi.getsettings.application;

import java.util.UUID;
import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsIllegalAccessException;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsNotFoundException;

public interface GetSettingsService {

  Settings execute(UUID userId) throws SettingsNotFoundException, SettingsIllegalAccessException;
}
