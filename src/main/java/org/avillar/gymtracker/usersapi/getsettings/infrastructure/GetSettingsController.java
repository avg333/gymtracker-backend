package org.avillar.gymtracker.usersapi.getsettings.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.usersapi.SettingsControllerDocumentation.SettingsControllerTag;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsIllegalAccessException;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsNotFoundException;
import org.avillar.gymtracker.usersapi.getsettings.infrastructure.GetSettingsControllerDocumentation.Methods.GetSettingsDocumentation;
import org.avillar.gymtracker.usersapi.getsettings.infrastructure.model.GetSettingsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SettingsControllerTag
@RequestMapping(path = "${usersApiPrefix}/v1")
public interface GetSettingsController {

  @GetSettingsDocumentation
  @GetMapping("/users/{userId}/settings")
  @ResponseStatus(HttpStatus.OK)
  GetSettingsResponse execute(@PathVariable UUID userId)
      throws SettingsNotFoundException, SettingsIllegalAccessException;
}
