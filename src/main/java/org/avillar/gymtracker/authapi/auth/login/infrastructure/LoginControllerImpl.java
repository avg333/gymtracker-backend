package org.avillar.gymtracker.authapi.auth.login.infrastructure;

import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.authapi.auth.login.application.AuthService;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.mapper.LoginControllerMapper;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginRequest;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginControllerImpl implements LoginController {

  private final AuthService authService;
  private final LoginControllerMapper loginControllerMapper;

  @Override
  public ResponseEntity<LoginResponse> execute(final LoginRequest loginRequest) {
    return ResponseEntity.ok(
        loginControllerMapper.map(authService.execute(loginControllerMapper.map(loginRequest))));
  }
}
