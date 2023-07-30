package org.avillar.gymtracker.authapi.register.infrastructure.mapper;

import org.avillar.gymtracker.authapi.register.application.model.RegisterRequestApplication;
import org.avillar.gymtracker.authapi.register.application.model.RegisterResponseApplication;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterRequest;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterControllerMapper {

  RegisterRequestApplication map(RegisterRequest registerRequest);

  RegisterResponse map(RegisterResponseApplication registerResponseApplication);
}