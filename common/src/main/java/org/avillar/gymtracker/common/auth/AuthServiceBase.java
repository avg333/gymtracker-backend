package org.avillar.gymtracker.common.auth;

import java.util.Objects;
import java.util.UUID;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.avillar.gymtracker.common.base.domain.BaseEntity;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AuthServiceBase {

  protected UUID getLoggedUserId() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new SecurityException("Attempt to access a resource with no user logged in!!");
    }

    final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return userDetails.getId();
  }

  protected <T extends BaseEntity> void checkParameters(
      final T entity, final AuthOperations authOperations) {
    if (Objects.isNull(authOperations)) {
      throw new IllegalArgumentException("The AuthOperations parameter cannot be null");
    }

    if (Objects.isNull(entity)) {
      throw new IllegalArgumentException("The entity cannot be null");
    }
  }
}
