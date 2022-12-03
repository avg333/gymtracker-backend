package org.avillar.gymtracker.auth.application;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.auth.application.jwt.JwtTokenUtil;
import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.measure.domain.Measure;
import org.avillar.gymtracker.program.domain.Program;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.user.application.UserAppDto;
import org.avillar.gymtracker.user.application.UserDetailsImpl;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.user.domain.UserDao;
import org.avillar.gymtracker.workout.domain.Workout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private static final String USER_NOT_FOUND = "The user does not exist";

    private static final String NO_PERMISSIONS = "El usuario logeado no tiene permisos para acceder al recurso";

    private final UserDao userDao;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthServiceImpl(UserDao userDao, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userDao = userDao;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    public UserAppDto login(final UserAppDto userAppDto) {
        if (this.userDao.findByUsername(userAppDto.getUsername()) == null) {
            throw new EntityNotFoundException(USER_NOT_FOUND);
        }

        final UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userAppDto.getUsername(), userAppDto.getPassword());

        final Authentication auth = this.authenticationManager.authenticate(token);
        final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        return new UserAppDto(this.jwtTokenUtil.generateToken(userDetails),
                userDetails.getUserApp().getId(), userDetails.getUserApp().getUsername(),
                null, null, null, null, null, null, null, null);
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    @Override
    public UserApp getLoggedUser() {
        try {
            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) auth.getPrincipal();
            return userDetailsImpl.getUserApp();
        } catch (Exception e) {
            LOGGER.error("Error al obtener el usuario logueado", e);
            return null;
        }
    }

    @Override
    public void checkAccess(final Exercise exercise) throws IllegalAccessException {
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
