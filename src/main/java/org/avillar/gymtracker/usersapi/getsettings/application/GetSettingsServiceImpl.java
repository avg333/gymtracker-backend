package org.avillar.gymtracker.usersapi.getsettings.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.usersapi.common.auth.application.AuthUsersService;
import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsIllegalAccessException;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsNotFoundException;
import org.avillar.gymtracker.usersapi.common.facade.settings.SettingsFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSettingsServiceImpl implements GetSettingsService {

  private final SettingsFacade settingsFacade;
  private final AuthUsersService authUsersService;

  @Override
  public Settings execute(final UUID userId)
      throws SettingsNotFoundException, SettingsIllegalAccessException {
    final Settings settings = settingsFacade.getSettingsByUserId(userId);

    authUsersService.checkAccess(settings, AuthOperations.READ);

    return settings;
  }
}
