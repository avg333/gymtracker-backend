package org.avillar.gymtracker.usersapi.getsettings.infrastructure.mapper;

import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.avillar.gymtracker.usersapi.getsettings.infrastructure.model.GetSettingsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GetSettingsInfrastructureMapper {

  GetSettingsResponse map(Settings settings);
}
