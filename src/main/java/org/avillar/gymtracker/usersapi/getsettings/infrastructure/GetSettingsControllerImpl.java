package org.avillar.gymtracker.usersapi.getsettings.infrastructure;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsIllegalAccessException;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsNotFoundException;
import org.avillar.gymtracker.usersapi.getsettings.application.GetSettingsService;
import org.avillar.gymtracker.usersapi.getsettings.infrastructure.mapper.GetSettingsInfrastructureMapper;
import org.avillar.gymtracker.usersapi.getsettings.infrastructure.model.GetSettingsResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GetSettingsControllerImpl implements GetSettingsController {

  private final GetSettingsService getSettingsService;
  private final GetSettingsInfrastructureMapper getSettingsInfrastructureMapper;

  @Override
  public GetSettingsResponse execute(final UUID userId)
      throws SettingsNotFoundException, SettingsIllegalAccessException {
    return getSettingsInfrastructureMapper.map(getSettingsService.execute(userId));
  }
}
