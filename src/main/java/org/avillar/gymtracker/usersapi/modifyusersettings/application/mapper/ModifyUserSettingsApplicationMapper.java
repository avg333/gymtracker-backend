package org.avillar.gymtracker.usersapi.modifyusersettings.application.mapper;

import org.avillar.gymtracker.usersapi.domain.Settings;
import org.avillar.gymtracker.usersapi.modifyusersettings.application.model.ModifyUserSettingsRequestApplication;
import org.avillar.gymtracker.usersapi.modifyusersettings.application.model.ModifyUserSettingsResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModifyUserSettingsApplicationMapper {

  Settings map(ModifyUserSettingsRequestApplication modifyUserSettingsRequestApplication);

  ModifyUserSettingsResponseApplication map(Settings settings);
}
