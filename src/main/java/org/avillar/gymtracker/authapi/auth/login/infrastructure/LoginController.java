package org.avillar.gymtracker.authapi.auth.login.infrastructure;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginControllerRequestInfrastructure;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginControllerResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Login", description = "API to manage Login")
@RequestMapping(path = "${authApiPrefix}/")
public interface LoginController {

  @PostMapping("${authApiEndpoint}")
  ResponseEntity<LoginControllerResponseInfrastructure> execute(
      @Valid @RequestBody
          LoginControllerRequestInfrastructure loginControllerRequestInfrastructure);
}
