package org.avillar.gymtracker.usersapi.modifysettings.infrastructure;

import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.usersapi.SettingsControllerDocumentation.SettingsControllerTag;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsIllegalAccessException;
import org.avillar.gymtracker.usersapi.modifysettings.infrastructure.ModifySettingsControllerDocumentation.Methods.ModifySettingsDocumentation;
import org.avillar.gymtracker.usersapi.modifysettings.infrastructure.model.ModifySettingsRequestDto;
import org.avillar.gymtracker.usersapi.modifysettings.infrastructure.model.ModifySettingsResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SettingsControllerTag
@RequestMapping(path = "${usersApiPrefix}/v1")
public interface ModifySettingsController {

  @ModifySettingsDocumentation
  @PostMapping("/users/{userId}/settings")
  @ResponseStatus(HttpStatus.OK)
  ModifySettingsResponseDto execute(
      @PathVariable UUID userId, @Valid @RequestBody ModifySettingsRequestDto request)
      throws SettingsIllegalAccessException;
}
