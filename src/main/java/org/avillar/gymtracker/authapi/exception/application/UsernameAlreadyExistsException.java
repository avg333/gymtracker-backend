package org.avillar.gymtracker.authapi.exception.application;

public class UsernameAlreadyExistsException extends RuntimeException {

  public UsernameAlreadyExistsException(String msg) {
    super(msg);
  }
}
