package org.avillar.gymtracker.authapi.auth.register.application.mapper;

import org.avillar.gymtracker.authapi.auth.register.application.model.RegisterResponseApplication;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterServiceMapper {

  RegisterResponseApplication map(UserDetailsImpl userDetails);
}
