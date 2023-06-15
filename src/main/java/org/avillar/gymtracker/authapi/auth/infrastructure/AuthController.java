package org.avillar.gymtracker.authapi.auth.infrastructure;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.authapi.auth.application.AuthService;
import org.avillar.gymtracker.authapi.auth.infrastructure.mapper.AuthControllerMapper;
import org.avillar.gymtracker.authapi.auth.infrastructure.model.AuthControllerRequest;
import org.avillar.gymtracker.authapi.auth.infrastructure.model.AuthControllerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${authApiPrefix}/")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final AuthControllerMapper authControllerMapper;

  @PostMapping("/signin")
  public ResponseEntity<AuthControllerResponse> login(
      @Valid @RequestBody final AuthControllerRequest authControllerRequest) {
    try {
      return ResponseEntity.ok(
          authControllerMapper.postResponse(
              authService.login(
                  authControllerMapper.postRequest(authControllerRequest))));
    } catch (BadCredentialsException e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } //TODO Gestionar excepciones mejor
  }
}
