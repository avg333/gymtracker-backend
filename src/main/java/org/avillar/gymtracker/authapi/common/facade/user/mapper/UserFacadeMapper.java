package org.avillar.gymtracker.authapi.common.facade.user.mapper;

import org.avillar.gymtracker.authapi.common.adapter.repository.model.UserEntity;
import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserFacadeMapper {

  UserApp map(UserEntity userEntity);

  UserEntity map(UserApp userApp);
}
