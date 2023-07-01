package org.avillar.gymtracker.authapi.auth.login.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.authapi.auth.login.application.AuthService;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.mapper.LoginControllerMapper;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginControllerRequestInfrastructure;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginControllerResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginControllerImpl implements LoginController {

  private final AuthService authService;
  private final LoginControllerMapper loginControllerMapper;

  @Override
  public ResponseEntity<LoginControllerResponseInfrastructure> execute(
      final LoginControllerRequestInfrastructure loginControllerRequestInfrastructure) {
    return ResponseEntity.ok(
        loginControllerMapper.map(
            authService.execute(loginControllerMapper.map(loginControllerRequestInfrastructure))));
  }
}
