package org.avillar.gymtracker.authapi.auth.login.application.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginControllerResponseApplication {

  private static final String type = "Bearer";
  private String token;

  private UUID id;
  private String username;
}
