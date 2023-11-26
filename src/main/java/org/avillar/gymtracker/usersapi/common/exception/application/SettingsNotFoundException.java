package org.avillar.gymtracker.usersapi.common.exception.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.usersapi.common.domain.Settings;

public class SettingsNotFoundException extends EntityNotFoundException {

  public SettingsNotFoundException(final UUID userId) {
    super(Settings.class, "userId", userId.toString());
  }
}
