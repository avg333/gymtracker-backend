package org.avillar.gymtracker.authapi.auth.infrastructure.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthControllerResponse {

  private static final String type = "Bearer";
  private String token;

  private UUID id;
  private String username;
}
