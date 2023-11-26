package org.avillar.gymtracker.authapi.common.exception.application;

public class UsernameAlreadyExistsException extends Exception {

  public UsernameAlreadyExistsException() {
    super("Username already in use");
  }
}
