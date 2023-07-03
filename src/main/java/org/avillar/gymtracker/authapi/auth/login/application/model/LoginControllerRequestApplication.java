package org.avillar.gymtracker.authapi.auth.login.application.model;

import lombok.Data;

@Data
public class LoginControllerRequestApplication {

  private String username;
  private String password;
}
