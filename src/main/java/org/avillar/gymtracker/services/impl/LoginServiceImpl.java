package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.config.security.MyUserDetails;
import org.avillar.gymtracker.model.entities.UserApp;
import org.avillar.gymtracker.services.LoginService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public UserApp getLoggedUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        return myUserDetails.getUserApp();
    }
}
