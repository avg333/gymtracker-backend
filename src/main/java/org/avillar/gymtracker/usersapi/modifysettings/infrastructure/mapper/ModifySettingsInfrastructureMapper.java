package org.avillar.gymtracker.usersapi.modifysettings.infrastructure.mapper;

import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.avillar.gymtracker.usersapi.modifysettings.infrastructure.model.ModifySettingsRequestDto;
import org.avillar.gymtracker.usersapi.modifysettings.infrastructure.model.ModifySettingsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ModifySettingsInfrastructureMapper {

  Settings map(ModifySettingsRequestDto modifySettingsRequestDto);

  ModifySettingsResponseDto map(Settings settings);
}
