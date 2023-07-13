package org.avillar.gymtracker.authapi.auth.register.application.model;

import lombok.Data;

@Data
public class RegisterRequestApplication {

  private String username;

  private String password;

  private String registerCode;
}
