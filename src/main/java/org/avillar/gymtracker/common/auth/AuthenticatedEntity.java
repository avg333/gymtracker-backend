package org.avillar.gymtracker.common.auth;

import java.util.UUID;

public interface AuthenticatedEntity {

  UUID getId();

  UUID getUserId();
}
