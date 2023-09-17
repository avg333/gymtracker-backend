package org.avillar.gymtracker.usersapi.auth.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.auth.AuthServiceBase;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.usersapi.domain.Settings;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUsersServiceImpl extends AuthServiceBase implements AuthUsersService {

  @Override
  public void checkAccess(final Settings settings, final AuthOperations authOperations) {
    checkParameters(settings, authOperations);

    final UUID userId = getLoggedUserId();
    if (!settings.getUserId().equals(userId)) {
      throw new IllegalAccessException(settings, authOperations, userId);
    }
  }
}
