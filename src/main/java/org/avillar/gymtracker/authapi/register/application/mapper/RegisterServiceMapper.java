package org.avillar.gymtracker.authapi.register.application.mapper;

import org.avillar.gymtracker.authapi.login.application.model.LoginRequestApplication;
import org.avillar.gymtracker.authapi.login.application.model.LoginResponseApplication;
import org.avillar.gymtracker.authapi.register.application.model.RegisterRequestApplication;
import org.avillar.gymtracker.authapi.register.application.model.RegisterResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterServiceMapper {

  LoginRequestApplication map(RegisterRequestApplication registerRequestApplication);

  RegisterResponseApplication map(LoginResponseApplication loginResponseApplication);
}
