package org.avillar.gymtracker.authapi.auth.application;

import org.avillar.gymtracker.authapi.auth.application.model.AuthControllerRequest;
import org.avillar.gymtracker.authapi.auth.application.model.AuthControllerResponse;

public interface AuthService {

  AuthControllerResponse login(AuthControllerRequest authControllerRequest);
}
