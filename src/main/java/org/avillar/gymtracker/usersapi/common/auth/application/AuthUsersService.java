package org.avillar.gymtracker.usersapi.common.auth.application;

import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsIllegalAccessException;

public interface AuthUsersService {

  void checkAccess(Settings settings, AuthOperations authOperations)
      throws SettingsIllegalAccessException;
}
