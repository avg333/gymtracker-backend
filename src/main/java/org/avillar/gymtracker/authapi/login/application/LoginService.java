package org.avillar.gymtracker.authapi.login.application;

import org.avillar.gymtracker.authapi.login.application.model.LoginRequestApplication;
import org.avillar.gymtracker.authapi.login.application.model.LoginResponseApplication;

public interface LoginService {

  LoginResponseApplication execute(LoginRequestApplication loginRequestApplication);
}
