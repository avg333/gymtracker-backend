package org.avillar.gymtracker.authapi.register.infrastructure.mapper;

import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterRequest;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegisterControllerMapper {

  UserApp map(RegisterRequest registerRequest);

  @Mapping(source = "token.value", target = "token")
  @Mapping(source = "token.type", target = "type")
  RegisterResponse map(UserApp userApp);
}
