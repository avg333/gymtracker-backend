package org.avillar.gymtracker.authapi.register.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;

@Data
public class RegisterResponse {

  @Schema(description = "Jwt type", example = "Bearer")
  private String type;

  @Schema(
      description = "User jwt",
      example =
          "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIyNzA5NjlhYi0yOGQ0LTRkMTktOGVhOC1kMmQ0YjA5MWM4ZDciLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiVVNFUl9ST0xFIn1dLCJzdWIiOiJjaGVtYSIsImp0aSI6IjI3MDk2OWFiLTI4ZDQtNGQxOS04ZWE4LWQyZDRiMDkxYzhkNyIsImlhdCI6MTY5MDQ5MjA0NCwiZXhwIjoxNjkwNTc4NDQ0fQ.0ZDErsUFyjg6mxrhNDt2nejnlKZ_5cyMurtNTODLgQw")
  private String token;

  @Schema(description = "User id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
  private UUID id;

  @Schema(description = "User username", example = "avillar")
  private String username;
}
