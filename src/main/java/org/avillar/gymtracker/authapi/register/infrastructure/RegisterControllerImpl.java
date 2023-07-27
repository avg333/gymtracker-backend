package org.avillar.gymtracker.authapi.register.infrastructure;

import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.authapi.register.application.RegisterService;
import org.avillar.gymtracker.authapi.register.infrastructure.mapper.RegisterControllerMapper;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterRequest;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterControllerImpl implements RegisterController {

  private final RegisterService registerService;
  private final RegisterControllerMapper registerControllerMapper;

  @Override
  public RegisterResponse execute(final RegisterRequest registerRequest) {
    return registerControllerMapper.map(
        registerService.execute(registerControllerMapper.map(registerRequest)));
  }
}
