package org.avillar.gymtracker.usersapi.auth.application;

import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.usersapi.domain.Settings;

public interface AuthUsersService {

  void checkAccess(Settings settings, AuthOperations authOperations);
}
