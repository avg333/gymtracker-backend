package org.avillar.gymtracker.authapi.auth.application.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthControllerResponse {

  private static final String type = "Bearer";
  private String token;

  private UUID id;
  private String username;
}
