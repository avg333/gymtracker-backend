package org.avillar.gymtracker.authapi.login.infrastructure;

import jakarta.validation.Valid;
import org.avillar.gymtracker.authapi.AuthControllerDocumentation.AuthControllerTag;
import org.avillar.gymtracker.authapi.login.infrastructure.LoginControllerDocumentation.Methods.LoginDocumentation;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginRequest;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@AuthControllerTag
@RequestMapping(path = "${authApiPrefix}")
public interface LoginController {

  @LoginDocumentation
  @PostMapping("${authApiEndpoint}")
  @ResponseStatus(HttpStatus.OK)
  LoginResponse execute(@Valid @RequestBody LoginRequest loginRequest);
}
