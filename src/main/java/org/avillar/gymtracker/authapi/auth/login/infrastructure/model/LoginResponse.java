package org.avillar.gymtracker.authapi.auth.login.infrastructure.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

  private static final String type = "Bearer";
  private String token;

  private UUID id;
  private String username;
}
