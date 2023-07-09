package org.avillar.gymtracker.authapi.auth.login.infrastructure.mapper;

import org.avillar.gymtracker.authapi.auth.login.application.model.LoginControllerRequestApplication;
import org.avillar.gymtracker.authapi.auth.login.application.model.LoginControllerResponseApplication;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginControllerRequest;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginControllerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginControllerMapper {

  LoginControllerResponse map(
      LoginControllerResponseApplication loginControllerResponseApplication);

  LoginControllerRequestApplication map(LoginControllerRequest loginControllerRequest);
}
