package org.avillar.gymtracker.usersapi.getusersettings.infrastructure.mapper;

import org.avillar.gymtracker.usersapi.getusersettings.application.model.GetUserSettingsResponseApplication;
import org.avillar.gymtracker.usersapi.getusersettings.infrastructure.model.GetUserSettingsResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetUserSettingsInfrastructureMapper {

  GetUserSettingsResponseInfrastructure map(
      GetUserSettingsResponseApplication getUserSettingsResponseApplication);
}
