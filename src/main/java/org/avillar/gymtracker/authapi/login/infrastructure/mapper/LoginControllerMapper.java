package org.avillar.gymtracker.authapi.login.infrastructure.mapper;

import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginRequest;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoginControllerMapper {

  @Mapping(source = "token.value", target = "token")
  @Mapping(source = "token.type", target = "type")
  LoginResponse map(UserApp userApp);

  UserApp map(LoginRequest loginRequest);
}
