package org.avillar.gymtracker.authapi.auth.infrastructure.mapper;

import org.avillar.gymtracker.authapi.auth.infrastructure.model.AuthControllerRequest;
import org.avillar.gymtracker.authapi.auth.infrastructure.model.AuthControllerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthControllerMapper {

  AuthControllerResponse postResponse(
      org.avillar.gymtracker.authapi.auth.application.model.AuthControllerResponse
          postWorkoutResponse);

  org.avillar.gymtracker.authapi.auth.application.model.AuthControllerRequest postRequest(
      AuthControllerRequest postWorkoutRequest);
}
