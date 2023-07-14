package org.avillar.gymtracker.authapi.login.infrastructure.model;

import java.util.UUID;
import lombok.Data;

@Data
public class LoginResponse {

  private static final String type = "Bearer";
  private String token;

  private UUID id;
  private String username;
}
