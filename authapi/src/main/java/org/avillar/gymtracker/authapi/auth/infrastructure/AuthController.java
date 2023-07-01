package org.avillar.gymtracker.authapi.auth.infrastructure;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.authapi.auth.application.AuthService;
import org.avillar.gymtracker.authapi.auth.infrastructure.mapper.AuthControllerMapper;
import org.avillar.gymtracker.authapi.auth.infrastructure.model.AuthControllerRequest;
import org.avillar.gymtracker.authapi.auth.infrastructure.model.AuthControllerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "${authApiPrefix}/")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final AuthControllerMapper authControllerMapper;

  @PostMapping("${authApiEndpoint}")
  public ResponseEntity<AuthControllerResponse> login(
      @Valid @RequestBody final AuthControllerRequest authControllerRequest) {
    try {
      return ResponseEntity.ok(
          authControllerMapper.postResponse(
              authService.login(authControllerMapper.postRequest(authControllerRequest))));
    } catch (AuthenticationException e) {
      log.info("Error identifying the user[" + authControllerRequest + "]:", e);
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    } catch (Exception e) {
      log.error("Error identifying the user[" + authControllerRequest + "]:", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
