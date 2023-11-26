package org.avillar.gymtracker.authapi.register.infrastructure;

import jakarta.validation.Valid;
import org.avillar.gymtracker.authapi.AuthControllerDocumentation.AuthControllerTag;
import org.avillar.gymtracker.authapi.common.exception.application.UsernameAlreadyExistsException;
import org.avillar.gymtracker.authapi.common.exception.application.WrongRegisterCodeException;
import org.avillar.gymtracker.authapi.register.infrastructure.RegisterControllerDocumentation.Methods.RegisterDocumentation;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterRequest;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@AuthControllerTag
@RequestMapping(path = "${authApiPrefix}")
public interface RegisterController {

  @RegisterDocumentation
  @PostMapping("${authApiRegisterEndpoint}")
  @ResponseStatus(HttpStatus.OK)
  RegisterResponse execute(@Valid @RequestBody RegisterRequest registerRequest)
      throws WrongRegisterCodeException, UsernameAlreadyExistsException;
}
