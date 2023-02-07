package org.avillar.gymtracker.auditing;

import org.avillar.gymtracker.user.application.UserDetailsImpl;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (null == auth) {
            return Optional.empty();
        }
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) auth.getPrincipal();
        return Optional.ofNullable(userDetailsImpl.getUserApp().getUsername());
    }
}
