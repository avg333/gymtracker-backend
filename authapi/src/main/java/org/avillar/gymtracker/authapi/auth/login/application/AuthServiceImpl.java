package org.avillar.gymtracker.authapi.auth.login.application;

import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.authapi.auth.login.application.mapper.LoginServiceMapper;
import org.avillar.gymtracker.authapi.auth.login.application.model.LoginControllerRequestApplication;
import org.avillar.gymtracker.authapi.auth.login.application.model.LoginControllerResponseApplication;
import org.avillar.gymtracker.common.auth.jwt.JwtTokenUtil;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final LoginServiceMapper loginServiceMapper;

  @Override
  public LoginControllerResponseApplication execute(
      final LoginControllerRequestApplication loginControllerRequestApplication) {
    final Authentication auth =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginControllerRequestApplication.getUsername(),
                loginControllerRequestApplication.getPassword()));

    final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    final String jwt = jwtTokenUtil.generateToken(userDetails);

    final LoginControllerResponseApplication loginControllerResponseApplication =
        loginServiceMapper.map(userDetails);
    loginControllerResponseApplication.setToken(jwt);
    return loginControllerResponseApplication;
  }
}
