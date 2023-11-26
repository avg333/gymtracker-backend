package org.avillar.gymtracker.usersapi.modifysettings.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsIllegalAccessException;
import org.avillar.gymtracker.usersapi.modifysettings.application.ModifySettingsService;
import org.avillar.gymtracker.usersapi.modifysettings.infrastructure.mapper.ModifySettingsInfrastructureMapper;
import org.avillar.gymtracker.usersapi.modifysettings.infrastructure.model.ModifySettingsRequestDto;
import org.avillar.gymtracker.usersapi.modifysettings.infrastructure.model.ModifySettingsResponseDto;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ModifySettingsControllerImpl implements ModifySettingsController {

  private final ModifySettingsService modifySettingsService;
  private final ModifySettingsInfrastructureMapper modifySettingsInfrastructureMapper;

  @Override
  public ModifySettingsResponseDto execute(
      final UUID userId, final ModifySettingsRequestDto request)
      throws SettingsIllegalAccessException {
    return modifySettingsInfrastructureMapper.map(
        modifySettingsService.execute(userId, modifySettingsInfrastructureMapper.map(request)));
  }
}
