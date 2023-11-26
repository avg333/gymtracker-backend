package org.avillar.gymtracker.authapi.common.facade.user;

import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.authapi.common.exception.application.UserNotFoundException;

public interface UserFacade {

  UserApp saveUser(UserApp userApp);

  UserApp findByUsername(String username) throws UserNotFoundException;
}
