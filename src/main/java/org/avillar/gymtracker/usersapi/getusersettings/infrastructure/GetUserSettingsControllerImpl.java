package org.avillar.gymtracker.usersapi.getusersettings.infrastructure;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.avillar.gymtracker.usersapi.getusersettings.application.GetUserSettingsService;
import org.avillar.gymtracker.usersapi.getusersettings.infrastructure.mapper.GetUserSettingsInfrastructureMapper;
import org.avillar.gymtracker.usersapi.getusersettings.infrastructure.model.GetUserSettingsResponseInfrastructure;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GetUserSettingsControllerImpl implements GetUserSettingsController {

  private final GetUserSettingsService getUserSettingsService;
  private final GetUserSettingsInfrastructureMapper getUserSettingsInfrastructureMapper;

  @Override
  public GetUserSettingsResponseInfrastructure execute(final UUID userId) {
    return getUserSettingsInfrastructureMapper.map(getUserSettingsService.execute(userId));
  }
}
