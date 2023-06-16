package org.avillar.gymtracker.common.auditing;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  @NonNull
  public Optional<String> getCurrentAuditor() {
    final SecurityContext securityContext = SecurityContextHolder.getContext();

    if (securityContext == null
        || securityContext.getAuthentication() == null
        || !securityContext.getAuthentication().isAuthenticated()) {
      return Optional.empty();
    }

    final UserDetailsImpl userDetails =
        (UserDetailsImpl) securityContext.getAuthentication().getPrincipal();
    return Optional.ofNullable(userDetails.getId().toString());
  }
}
