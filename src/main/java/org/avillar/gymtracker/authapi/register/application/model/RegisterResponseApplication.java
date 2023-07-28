package org.avillar.gymtracker.authapi.register.application.model;

import java.util.UUID;
import lombok.Data;

@Data
public class RegisterResponseApplication {

  private String type;

  private String token;

  private UUID id;

  private String username;
}
