package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.config.security.MyUserDetails;
import org.avillar.gymtracker.model.entities.Measure;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.UserApp;
import org.avillar.gymtracker.model.entities.Workout;
import org.avillar.gymtracker.services.LoginService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private static final String NO_PERMISSIONS = "El usuario logeado no tiene permisos para acceder al recurso";

    @Override
    public UserApp getLoggedUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        return myUserDetails.getUserApp();
    }

    @Override
    public void checkAccess(final Measure measure) throws IllegalAccessException {
        final UserApp userApp = this.getLoggedUser();
        if (userApp == null) {
            throw new IllegalAccessException(NO_PERMISSIONS);
        }

        if (measure != null && !userApp.equals(measure.getUserApp())) {
            throw new IllegalAccessException(NO_PERMISSIONS);
        }
    }

    @Override
    public void checkAccess(final Program program) throws IllegalAccessException {
        final UserApp userApp = this.getLoggedUser();
        if (userApp == null) {
            throw new IllegalAccessException(NO_PERMISSIONS);
        }

        if (program != null && !userApp.equals(program.getUserApp())) {
            throw new IllegalAccessException(NO_PERMISSIONS);
        }
    }

    @Override
    public void checkAccess(final Workout workout) throws IllegalAccessException {
        final UserApp userApp = this.getLoggedUser();
        if (userApp == null) {
            throw new IllegalAccessException(NO_PERMISSIONS);
        }

        if (workout != null && !userApp.equals(workout.getUserApp())) {
            throw new IllegalAccessException(NO_PERMISSIONS);
        }
    }
}
