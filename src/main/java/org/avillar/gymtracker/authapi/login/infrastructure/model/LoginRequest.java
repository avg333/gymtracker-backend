package org.avillar.gymtracker.authapi.login.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

  @NotBlank(message = "The username is obligatory")
  @Schema(description = "User username", example = "avillar")
  private String username;

  @NotBlank(message = "The password is obligatory")
  @Schema(description = "User password", example = "avillarpass")
  private String password;
}
