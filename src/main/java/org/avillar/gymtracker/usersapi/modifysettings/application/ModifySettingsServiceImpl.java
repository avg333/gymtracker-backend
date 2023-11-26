package org.avillar.gymtracker.usersapi.modifysettings.application;

import java.util.Collections;
import java.util.Optional;
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
public class ModifySettingsServiceImpl implements ModifySettingsService {

  private final SettingsFacade settingsFacade;
  private final AuthUsersService authUsersService;

  private static AuthOperations getAuthOperations(Settings settings) {
    return settings.getId() == null ? AuthOperations.CREATE : AuthOperations.UPDATE;
  }

  @Override
  public Settings execute(final UUID userId, final Settings settings)
      throws SettingsIllegalAccessException {

    settings.setUserId(userId);

    settings.setId(getSettingsIdIfExists(userId).orElse(null));

    authUsersService.checkAccess(settings, getAuthOperations(settings));

    Collections.sort(settings.getSelectedPlates());

    return settingsFacade.saveSettings(settings);
  }

  private Optional<UUID> getSettingsIdIfExists(final UUID userId) {
    try {
      return Optional.ofNullable(settingsFacade.getSettingsByUserId(userId)).map(Settings::getId);
    } catch (SettingsNotFoundException e) {
      return Optional.empty();
    }
  }
}
