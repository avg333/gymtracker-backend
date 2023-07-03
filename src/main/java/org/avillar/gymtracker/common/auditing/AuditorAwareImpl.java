package org.avillar.gymtracker.common.auditing;

import java.util.Optional;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  @NonNull
  public Optional<String> getCurrentAuditor() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return Optional.empty();
    }

    final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return Optional.ofNullable(userDetails.getId().toString());
  }
}
