package org.avillar.gymtracker.usersapi.common.auth.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.auth.AuthServiceBase;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsIllegalAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUsersServiceImpl extends AuthServiceBase implements AuthUsersService {

  @Override
  public void checkAccess(final Settings settings, final AuthOperations authOperations)
      throws SettingsIllegalAccessException {
    checkParameters(settings, authOperations);

    final UUID userId = getLoggedUserId();
    if (!settings.getUserId().equals(userId)) {
      throw new SettingsIllegalAccessException(settings.getId(), authOperations, userId);
    }
  }
}
