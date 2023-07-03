package org.avillar.gymtracker.authapi.auth.login.application.mapper;

import org.avillar.gymtracker.authapi.auth.login.application.model.LoginControllerResponseApplication;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginServiceMapper {

  LoginControllerResponseApplication map(UserDetailsImpl userDetails);
}
