package org.avillar.gymtracker.authapi.login.application.model;

import lombok.Data;

@Data
public class LoginRequestApplication {

  private String username;
  private String password;
}
