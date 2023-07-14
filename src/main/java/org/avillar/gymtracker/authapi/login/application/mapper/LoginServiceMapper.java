package org.avillar.gymtracker.authapi.login.application.mapper;

import org.avillar.gymtracker.authapi.login.application.model.LoginResponseApplication;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginServiceMapper {

  LoginResponseApplication map(UserDetailsImpl userDetails);
}
