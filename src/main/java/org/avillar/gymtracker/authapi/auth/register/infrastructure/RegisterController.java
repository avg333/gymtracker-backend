package org.avillar.gymtracker.authapi.auth.register.infrastructure;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.avillar.gymtracker.authapi.auth.register.infrastructure.model.RegisterRequest;
import org.avillar.gymtracker.authapi.auth.register.infrastructure.model.RegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Register", description = "API to manage Register")
@RequestMapping(path = "${authApiPrefix}")
public interface RegisterController {

  @PostMapping("${authApiRegisterEndpoint}")
  ResponseEntity<RegisterResponse> execute(@Valid @RequestBody RegisterRequest registerRequest);
}
