package org.avillar.gymtracker.usersapi.modifyusersettings.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.usersapi.auth.application.AuthUsersService;
import org.avillar.gymtracker.usersapi.domain.Settings;
import org.avillar.gymtracker.usersapi.domain.SettingsDao;
import org.avillar.gymtracker.usersapi.modifyusersettings.application.mapper.ModifyUserSettingsApplicationMapper;
import org.avillar.gymtracker.usersapi.modifyusersettings.application.model.ModifyUserSettingsRequestApplication;
import org.avillar.gymtracker.usersapi.modifyusersettings.application.model.ModifyUserSettingsResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModifyUserSettingsServiceImpl implements ModifyUserSettingsService {

  private final SettingsDao settingsDao;
  private final ModifyUserSettingsApplicationMapper modifyUserSettingsApplicationMapper;
  private final AuthUsersService authUsersService;

  @Override
  public ModifyUserSettingsResponseApplication execute(
      final UUID userId,
      final ModifyUserSettingsRequestApplication modifyUserSettingsRequestApplication) {

    final Settings settings =
        modifyUserSettingsApplicationMapper.map(modifyUserSettingsRequestApplication);
    settings.setUserId(userId);

    //TODO Validate settings

    authUsersService.checkAccess(settings, AuthOperations.CREATE);

    settingsDao.findByUserId(userId).stream()
        .findAny()
        .ifPresent(value -> settings.setId(value.getId()));

    settingsDao.save(settings);

    return modifyUserSettingsApplicationMapper.map(settings);
  }
}
