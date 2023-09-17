package org.avillar.gymtracker.usersapi.getusersettings.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.usersapi.auth.application.AuthUsersService;
import org.avillar.gymtracker.usersapi.domain.Settings;
import org.avillar.gymtracker.usersapi.domain.SettingsDao;
import org.avillar.gymtracker.usersapi.getusersettings.application.mapper.GetUserSettingsApplicationMapper;
import org.avillar.gymtracker.usersapi.getusersettings.application.model.GetUserSettingsResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserSettingsServiceImpl implements GetUserSettingsService {

  private final SettingsDao settingsDao;
  private final AuthUsersService authUsersService;
  private final GetUserSettingsApplicationMapper getUserSettingsApplicationMapper;

  @Override
  public GetUserSettingsResponseApplication execute(final UUID userId) {
    final Settings settings =
        settingsDao.findByUserId(userId).stream()
            .findAny()
            .orElseThrow(() -> new EntityNotFoundException(Settings.class, userId));

    authUsersService.checkAccess(settings, AuthOperations.READ);

    return getUserSettingsApplicationMapper.map(settings);
  }
}
