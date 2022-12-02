package org.avillar.gymtracker.auth.application;

import org.avillar.gymtracker.auth.application.jwt.JwtTokenUtil;
import org.avillar.gymtracker.measure.domain.Measure;
import org.avillar.gymtracker.program.domain.Program;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.user.application.UserAppDto;
import org.avillar.gymtracker.user.application.UserDetailsImpl;
import org.avillar.gymtracker.user.application.UserDetailsServiceImpl;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.workout.domain.Workout;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    final UserDetailsServiceImpl userDetailsService;
    final JwtTokenUtil jwtTokenUtil;
    private static final String NO_PERMISSIONS = "El usuario logeado no tiene permisos para acceder al recurso";

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserDetailsServiceImpl userDetailsService,
                           JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    public UserAppDto login(final UserAppDto userAppDto) {
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userAppDto.getUsername(), userAppDto.getPassword());

        final Authentication auth = this.authenticationManager.authenticate(token);

        if (!auth.isAuthenticated()) {
            throw new BadCredentialsException("");
        }

        final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return new UserAppDto(this.jwtTokenUtil.generateToken(userDetails),
                userDetails.getUserApp().getId(), userDetails.getUsername(), null, null, null,
                null, null, null, null, null);

    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    @Override
    public UserApp getLoggedUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) auth.getPrincipal();
        return userDetailsImpl.getUserApp();
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
