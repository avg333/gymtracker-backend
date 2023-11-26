package org.avillar.gymtracker.authapi.common.exception.application;

import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

  public UserNotFoundException(final String username) {
    super(UserApp.class, "username", username);
  }
}
