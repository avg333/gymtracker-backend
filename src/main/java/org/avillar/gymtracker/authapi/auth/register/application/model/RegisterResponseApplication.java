package org.avillar.gymtracker.authapi.auth.register.application.model;

import java.util.UUID;
import lombok.Data;

@Data
public class RegisterResponseApplication {

  private static final String type = "Bearer";
  private String token;

  private UUID id;
  private String username;
}
