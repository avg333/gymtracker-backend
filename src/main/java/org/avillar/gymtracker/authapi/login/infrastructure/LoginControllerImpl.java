package org.avillar.gymtracker.authapi.login.infrastructure;

import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.authapi.login.application.LoginService;
import org.avillar.gymtracker.authapi.login.infrastructure.mapper.LoginControllerMapper;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginRequest;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginControllerImpl implements LoginController {

  private final LoginService loginService;
  private final LoginControllerMapper loginControllerMapper;

  @Override
  public LoginResponse execute(final LoginRequest loginRequest) {
    return loginControllerMapper.map(loginService.execute(loginControllerMapper.map(loginRequest)));
  }
}
