package org.avillar.gymtracker.authapi.auth.application.mapper;

import org.avillar.gymtracker.authapi.auth.application.model.AuthControllerResponse;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthServiceMapper {

  AuthControllerResponse postResponse(UserDetailsImpl userDetails);
}
