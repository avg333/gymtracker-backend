package org.avillar.gymtracker.auditing;

import org.avillar.gymtracker.security.MyUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (null == auth) {
            return Optional.empty();
        }
        final MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        return Optional.ofNullable(myUserDetails.getUserApp().getUsername());
    }
}
