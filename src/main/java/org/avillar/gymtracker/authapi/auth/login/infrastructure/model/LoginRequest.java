package org.avillar.gymtracker.authapi.auth.login.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

  @NotNull(message = "The username is obligatory")
  private String username;

  @NotNull(message = "The password is obligatory")
  private String password;
}
