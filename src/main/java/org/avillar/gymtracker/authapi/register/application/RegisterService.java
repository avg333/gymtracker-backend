package org.avillar.gymtracker.authapi.register.application;

import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.authapi.common.exception.application.UsernameAlreadyExistsException;
import org.avillar.gymtracker.authapi.common.exception.application.WrongRegisterCodeException;

public interface RegisterService {

  UserApp execute(UserApp userApp, String registerCode)
      throws WrongRegisterCodeException, UsernameAlreadyExistsException;
}
