package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.config.security.MyUserDetails;
import org.avillar.gymtracker.model.entities.*;
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
        if (null == userApp) {
            throw new IllegalAccessException(NO_PERMISSIONS);
        }

        if (null != measure && !userApp.equals(measure.getUserApp())) {
            throw new IllegalAccessException(NO_PERMISSIONS);
        }
    }

    @Override
    public void checkAccess(final Program program) throws IllegalAccessException {
        final UserApp userApp = this.getLoggedUser();
        if (null == userApp) {
            throw new IllegalAccessException(NO_PERMISSIONS);
        }

        if (null != program && !userApp.equals(program.getUserApp())) {
            throw new IllegalAccessException(NO_PERMISSIONS);
        }
    }

    @Override
    public void checkAccess(final Workout workout) throws IllegalAccessException {
        final UserApp userApp = this.getLoggedUser();
        if (null == userApp) {
            throw new IllegalAccessException(NO_PERMISSIONS);
        }

        if (null != workout && !userApp.equals(workout.getUserApp())) {
            throw new IllegalAccessException(NO_PERMISSIONS);
        }
    }

    @Override
    public void checkAccess(final SetGroup setGroup) throws IllegalAccessException {
        final UserApp userApp = this.getLoggedUser();
        if (null == userApp) {
            throw new IllegalAccessException(NO_PERMISSIONS);
        }

        if (null != setGroup.getSession())
            this.checkAccess(setGroup.getSession().getProgram());
        else if (null != setGroup.getWorkout()) {
            this.checkAccess(setGroup.getWorkout());
        }
    }
}
