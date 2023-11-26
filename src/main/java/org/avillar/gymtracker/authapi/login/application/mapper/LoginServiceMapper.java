package org.avillar.gymtracker.authapi.login.application.mapper;

import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoginServiceMapper {

  UserApp map(UserDetailsImpl userDetails);

  default UsernamePasswordAuthenticationToken map(final UserApp userApp) {
    if (userApp == null) {
      return null;
    }

    return new UsernamePasswordAuthenticationToken(userApp.getUsername(), userApp.getPassword());
  }
}
