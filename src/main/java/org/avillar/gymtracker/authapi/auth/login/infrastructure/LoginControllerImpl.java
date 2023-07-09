package org.avillar.gymtracker.authapi.auth.login.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.authapi.auth.login.application.AuthService;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.mapper.LoginControllerMapper;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginControllerRequest;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginControllerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginControllerImpl implements LoginController {

  private final AuthService authService;
  private final LoginControllerMapper loginControllerMapper;

  @Override
  public ResponseEntity<LoginControllerResponse> execute(
      final LoginControllerRequest loginControllerRequest) {
    return ResponseEntity.ok(
        loginControllerMapper.map(
            authService.execute(loginControllerMapper.map(loginControllerRequest))));
  }
}
