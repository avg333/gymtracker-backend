package org.avillar.gymtracker.authapi.login.application;

import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.authapi.login.application.mapper.LoginServiceMapper;
import org.avillar.gymtracker.authapi.login.application.model.LoginRequestApplication;
import org.avillar.gymtracker.authapi.login.application.model.LoginResponseApplication;
import org.avillar.gymtracker.common.auth.jwt.JwtTokenUtil;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final LoginServiceMapper loginServiceMapper;

  @Override
  public LoginResponseApplication execute(final LoginRequestApplication loginRequestApplication) {
    final Authentication auth =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequestApplication.getUsername(), loginRequestApplication.getPassword()));

    final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

    final LoginResponseApplication loginResponseApplication = loginServiceMapper.map(userDetails);
    loginResponseApplication.setToken(jwtTokenUtil.generateToken(userDetails));
    loginResponseApplication.setType(jwtTokenUtil.getTokenType());
    return loginResponseApplication;
  }
}
