package org.avillar.gymtracker.usersapi.modifyusersettings.infrastructure.mapper;

import org.avillar.gymtracker.usersapi.modifyusersettings.application.model.ModifyUserSettingsRequestApplication;
import org.avillar.gymtracker.usersapi.modifyusersettings.application.model.ModifyUserSettingsResponseApplication;
import org.avillar.gymtracker.usersapi.modifyusersettings.infrastructure.model.ModifyUserSettingsRequestInfrastructure;
import org.avillar.gymtracker.usersapi.modifyusersettings.infrastructure.model.ModifyUserSettingsResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModifyUserSettingsInfrastructureMapper {

  ModifyUserSettingsRequestApplication map(
      ModifyUserSettingsRequestInfrastructure modifyUserSettingsRequestInfrastructure);

  ModifyUserSettingsResponseInfrastructure map(
      ModifyUserSettingsResponseApplication modifyUserSettingsResponseApplication);
}
