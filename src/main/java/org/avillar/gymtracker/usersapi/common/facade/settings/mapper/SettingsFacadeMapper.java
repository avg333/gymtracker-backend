package org.avillar.gymtracker.usersapi.common.facade.settings.mapper;

import org.avillar.gymtracker.usersapi.common.adapter.repository.model.SettingsEntity;
import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface SettingsFacadeMapper {

  Settings map(SettingsEntity settingsEntity);

  SettingsEntity map(Settings settings);
}
