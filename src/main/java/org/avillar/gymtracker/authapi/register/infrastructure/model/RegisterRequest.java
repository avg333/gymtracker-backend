package org.avillar.gymtracker.authapi.register.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {

  @NotNull(message = "The username is obligatory")
  private String username;

  @NotNull(message = "The password is obligatory")
  private String password;

  private String registerCode;
}
