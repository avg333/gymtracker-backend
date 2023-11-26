package org.avillar.gymtracker.authapi.login.application;

import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.authapi.common.domain.Token;
import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.authapi.login.application.mapper.LoginServiceMapper;
import org.avillar.gymtracker.common.auth.jwt.JwtTokenUtil;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final LoginServiceMapper loginServiceMapper;

  @Override
  public UserApp execute(final UserApp userApp) {
    final Authentication auth = authenticationManager.authenticate(loginServiceMapper.map(userApp));

    final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

    final UserApp loginResponse = loginServiceMapper.map(userDetails);

    loginResponse.setToken(
        Token.builder()
            .value(jwtTokenUtil.generateToken(userDetails))
            .type(jwtTokenUtil.getTokenType())
            .build());

    return loginResponse;
  }
}
