package org.avillar.gymtracker.authapi.auth.login.infrastructure.mapper;

import org.avillar.gymtracker.authapi.auth.login.application.model.LoginControllerRequestApplication;
import org.avillar.gymtracker.authapi.auth.login.application.model.LoginControllerResponseApplication;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginControllerRequestInfrastructure;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginControllerResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginControllerMapper {

  LoginControllerResponseInfrastructure map(
      LoginControllerResponseApplication loginControllerResponseApplication);

  LoginControllerRequestApplication map(
      LoginControllerRequestInfrastructure loginControllerRequestInfrastructure);
}