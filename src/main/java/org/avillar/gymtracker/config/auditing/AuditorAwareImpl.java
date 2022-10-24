package org.avillar.gymtracker.config.auditing;

import org.avillar.gymtracker.config.security.MyUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return Optional.empty();
        }
        final MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        return Optional.ofNullable(myUserDetails.getUserApp().getUsername());
    }
}
