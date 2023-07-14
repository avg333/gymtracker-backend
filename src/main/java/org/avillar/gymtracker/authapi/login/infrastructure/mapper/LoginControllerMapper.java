package org.avillar.gymtracker.authapi.login.infrastructure.mapper;

import org.avillar.gymtracker.authapi.login.application.model.LoginRequestApplication;
import org.avillar.gymtracker.authapi.login.application.model.LoginResponseApplication;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginRequest;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginControllerMapper {

  LoginResponse map(LoginResponseApplication loginResponseApplication);

  LoginRequestApplication map(LoginRequest loginRequest);
}
