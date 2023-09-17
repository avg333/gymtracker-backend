package org.avillar.gymtracker.usersapi.modifyusersettings.application;

import java.util.UUID;
import org.avillar.gymtracker.usersapi.modifyusersettings.application.model.ModifyUserSettingsRequestApplication;
import org.avillar.gymtracker.usersapi.modifyusersettings.application.model.ModifyUserSettingsResponseApplication;

public interface ModifyUserSettingsService {

  ModifyUserSettingsResponseApplication execute(
      UUID userId, ModifyUserSettingsRequestApplication modifyUserSettingsRequestApplication);
}
