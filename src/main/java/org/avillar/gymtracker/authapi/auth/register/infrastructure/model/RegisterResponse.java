package org.avillar.gymtracker.authapi.auth.register.infrastructure.model;

import java.util.UUID;
import lombok.Data;

@Data
public class RegisterResponse {

  private static final String type = "Bearer";
  private String token;

  private UUID id;
  private String username;
}
