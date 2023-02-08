package org.avillar.gymtracker.auth.application;

import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.auth.application.jwt.JwtTokenUtil;
import org.avillar.gymtracker.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.measure.domain.Measure;
import org.avillar.gymtracker.program.domain.Program;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.user.application.UserAppDto;
import org.avillar.gymtracker.user.application.UserDetailsImpl;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.user.domain.UserDao;
import org.avillar.gymtracker.workout.domain.Workout;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

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
            throw new EntityNotFoundException(UserApp.class, "userName", userAppDto.getUsername());
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
            log.error("Error al obtener el usuario logueado", e);
            throw new EntityNotFoundException(UserApp.class, "null", "null"); //TODO
        }
    }

    @Override
    public void checkAccess(final Exercise exercise) throws IllegalAccessException {
        this.getLoggedUser();

        //TODO
    }

    @Override
    public void checkAccess(final Measure measure) throws IllegalAccessException {
        final UserApp userApp = this.getLoggedUser();

        if (null != measure && !userApp.getId().equals(measure.getUserApp().getId())) {
            throw new IllegalAccessException(measure, "READ", userApp);
        }
    }

    @Override
    public void checkAccess(final Program program) throws IllegalAccessException {
        final UserApp userApp = this.getLoggedUser();

        if (null != program && !userApp.getId().equals(program.getUserApp().getId())) {
            throw new IllegalAccessException(program, "READ", userApp);
        }
    }

    @Override
    public void checkAccess(final Workout workout) throws IllegalAccessException {
        final UserApp userApp = this.getLoggedUser();

        if (null != workout && !userApp.getId().equals(workout.getUserApp().getId())) {
            throw new IllegalAccessException(workout, "READ", userApp);
        }
    }

    @Override
    public void checkAccess(final SetGroup setGroup) throws IllegalAccessException {
        if (null != setGroup.getSession())
            this.checkAccess(setGroup.getSession().getProgram());
        else if (null != setGroup.getWorkout()) {
            this.checkAccess(setGroup.getWorkout());
        }
    }
}
