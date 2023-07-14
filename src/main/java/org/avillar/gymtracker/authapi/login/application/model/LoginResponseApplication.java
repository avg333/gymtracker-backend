package org.avillar.gymtracker.authapi.login.application.model;

import java.util.UUID;
import lombok.Data;

@Data
public class LoginResponseApplication {

  private static final String type = "Bearer";
  private String token;

  private UUID id;
  private String username;
}
