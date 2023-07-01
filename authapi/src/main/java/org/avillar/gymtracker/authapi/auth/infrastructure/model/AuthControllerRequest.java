package org.avillar.gymtracker.authapi.auth.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthControllerRequest {

  @NotNull(message = "The username is obligatory")
  private String username;

  @NotNull(message = "The password is obligatory")
  private String password;
}
