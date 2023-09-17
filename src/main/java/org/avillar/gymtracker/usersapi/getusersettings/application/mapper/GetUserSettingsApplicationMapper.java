package org.avillar.gymtracker.usersapi.getusersettings.application.mapper;

import org.avillar.gymtracker.usersapi.domain.Settings;
import org.avillar.gymtracker.usersapi.getusersettings.application.model.GetUserSettingsResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetUserSettingsApplicationMapper {

  GetUserSettingsResponseApplication map(Settings settings);
}
