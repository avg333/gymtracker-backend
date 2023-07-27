package org.avillar.gymtracker.authapi.register.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

  @NotBlank(message = "The username is obligatory")
  @Size(min = 5, max = 50, message = "The username length must be between 5 and 50 characters")
  @Schema(description = "User username", example = "avillar")
  private String username;

  @NotBlank(message = "The password is obligatory")
  @Size(min = 5, max = 50, message = "The username password must be between 0 and 50 characters")
  @Schema(description = "User password", example = "avillarpass")
  private String password;

  @Schema(description = "Register code", example = "FREE2024")
  private String registerCode;
}
