package org.avillar.gymtracker.auth.application;

import org.avillar.gymtracker.measure.domain.Measure;
import org.avillar.gymtracker.program.domain.Program;
import org.avillar.gymtracker.security.MyUserDetails;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.workout.domain.Workout;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
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
