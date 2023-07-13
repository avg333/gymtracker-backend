package org.avillar.gymtracker.authapi.auth.login.infrastructure.mapper;

import org.avillar.gymtracker.authapi.auth.login.application.model.LoginControllerRequestApplication;
import org.avillar.gymtracker.authapi.auth.login.application.model.LoginControllerResponseApplication;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginRequest;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginControllerMapper {

  LoginResponse map(LoginControllerResponseApplication loginControllerResponseApplication);

  LoginControllerRequestApplication map(LoginRequest loginRequest);
}
