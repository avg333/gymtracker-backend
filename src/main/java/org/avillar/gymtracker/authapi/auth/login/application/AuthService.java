package org.avillar.gymtracker.authapi.auth.login.application;

import org.avillar.gymtracker.authapi.auth.login.application.model.LoginControllerRequestApplication;
import org.avillar.gymtracker.authapi.auth.login.application.model.LoginControllerResponseApplication;

public interface AuthService {

  LoginControllerResponseApplication execute(
      LoginControllerRequestApplication loginControllerRequestApplication);
}
