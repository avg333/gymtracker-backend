package org.avillar.gymtracker.authapi.exception.application;

public class WrongRegisterCodeException extends RuntimeException {

  public WrongRegisterCodeException(String msg) {
    super(msg);
  }
}
