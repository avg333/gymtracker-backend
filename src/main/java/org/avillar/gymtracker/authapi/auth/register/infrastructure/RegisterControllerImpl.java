package org.avillar.gymtracker.authapi.auth.register.infrastructure;

import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.authapi.auth.register.application.RegisterService;
import org.avillar.gymtracker.authapi.auth.register.infrastructure.mapper.RegisterControllerMapper;
import org.avillar.gymtracker.authapi.auth.register.infrastructure.model.RegisterRequest;
import org.avillar.gymtracker.authapi.auth.register.infrastructure.model.RegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterControllerImpl implements RegisterController {

  private final RegisterService registerService;
  private final RegisterControllerMapper registerControllerMapper;

  @Override
  public ResponseEntity<RegisterResponse> execute(final RegisterRequest registerRequest) {
    return ResponseEntity.ok(
        registerControllerMapper.map(
            registerService.execute(registerControllerMapper.map(registerRequest))));
  }
}
