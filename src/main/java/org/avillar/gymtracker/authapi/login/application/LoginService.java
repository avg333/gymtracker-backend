package org.avillar.gymtracker.authapi.login.application;

import org.avillar.gymtracker.authapi.common.domain.UserApp;

public interface LoginService {

  UserApp execute(UserApp userApp);
}
