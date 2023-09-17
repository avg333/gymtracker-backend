package org.avillar.gymtracker.usersapi.getusersettings.application;

import java.util.UUID;
import org.avillar.gymtracker.usersapi.getusersettings.application.model.GetUserSettingsResponseApplication;

public interface GetUserSettingsService {

  GetUserSettingsResponseApplication execute(UUID userId);
}
