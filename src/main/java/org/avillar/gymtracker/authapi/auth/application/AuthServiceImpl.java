package org.avillar.gymtracker.authapi.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.authapi.auth.application.mapper.AuthServiceMapper;
import org.avillar.gymtracker.authapi.auth.application.model.AuthControllerRequest;
import org.avillar.gymtracker.authapi.auth.application.model.AuthControllerResponse;
import org.avillar.gymtracker.common.auth.jwt.JwtTokenUtil;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthServiceMapper authServiceMapper;

    @Override
    public AuthControllerResponse login(final AuthControllerRequest authControllerRequest) {
        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authControllerRequest.getUsername(), authControllerRequest.getPassword());
        final Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        final AuthControllerResponse authControllerResponse = authServiceMapper.postResponse(userDetails);
        authControllerResponse.setToken(jwt);
        return authControllerResponse;
    }
}
