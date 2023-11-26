package org.avillar.gymtracker.authapi.common.exception.application;

public class WrongRegisterCodeException extends Exception {

  public WrongRegisterCodeException() {
    super("Wrong registration code");
  }
}
