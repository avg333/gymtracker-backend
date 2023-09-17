package org.avillar.gymtracker.usersapi.modifyusersettings.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.usersapi.modifyusersettings.application.ModifyUserSettingsService;
import org.avillar.gymtracker.usersapi.modifyusersettings.infrastructure.mapper.ModifyUserSettingsInfrastructureMapper;
import org.avillar.gymtracker.usersapi.modifyusersettings.infrastructure.model.ModifyUserSettingsRequestInfrastructure;
import org.avillar.gymtracker.usersapi.modifyusersettings.infrastructure.model.ModifyUserSettingsResponseInfrastructure;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ModifyUserSettingsControllerImpl implements ModifyUserSettingsController {

  private final ModifyUserSettingsService modifyUserSettingsService;
  private final ModifyUserSettingsInfrastructureMapper modifyUserSettingsInfrastructureMapper;

  @Override
  public ModifyUserSettingsResponseInfrastructure execute(
      final UUID userId, final ModifyUserSettingsRequestInfrastructure request) {
    return modifyUserSettingsInfrastructureMapper.map(
        modifyUserSettingsService.execute(
            userId, modifyUserSettingsInfrastructureMapper.map(request)));
  }
}
