package org.avillar.gymtracker.authapi.common.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserApp {

  private UUID id;

  private String username;

  private String password;

  private Token token;
}
