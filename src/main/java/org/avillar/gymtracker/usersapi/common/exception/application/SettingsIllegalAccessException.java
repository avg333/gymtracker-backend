package org.avillar.gymtracker.usersapi.common.exception.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.usersapi.common.domain.Settings;

public class SettingsIllegalAccessException extends IllegalAccessException {

  public SettingsIllegalAccessException(
      UUID settingsId, AuthOperations authOperations, UUID userId) {
    super(Settings.class, settingsId, authOperations, userId);
  }
}
