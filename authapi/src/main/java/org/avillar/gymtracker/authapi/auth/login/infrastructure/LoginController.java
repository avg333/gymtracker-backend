package org.avillar.gymtracker.authapi.auth.login.infrastructure;

import jakarta.validation.Valid;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginControllerRequestInfrastructure;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginControllerResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${authApiPrefix}/")
public interface LoginController {

  @PostMapping("${authApiEndpoint}")
  ResponseEntity<LoginControllerResponseInfrastructure> execute(
      @Valid @RequestBody
          LoginControllerRequestInfrastructure loginControllerRequestInfrastructure);
}
