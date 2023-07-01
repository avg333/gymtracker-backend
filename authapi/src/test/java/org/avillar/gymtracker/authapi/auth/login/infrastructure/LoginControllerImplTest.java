package org.avillar.gymtracker.authapi.auth.login.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.authapi.auth.login.application.AuthService;
import org.avillar.gymtracker.authapi.auth.login.application.model.LoginControllerResponseApplication;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.mapper.LoginControllerMapperImpl;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginControllerRequestInfrastructure;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginControllerResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class LoginControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private LoginControllerImpl loginController;

  @Mock private AuthService authService;
  @Spy private LoginControllerMapperImpl loginControllerMapper;

  @Test
  void loginOk() {
    final LoginControllerRequestInfrastructure request =
        easyRandom.nextObject(LoginControllerRequestInfrastructure.class);
    final LoginControllerResponseApplication expected =
        easyRandom.nextObject(LoginControllerResponseApplication.class);

    when(authService.execute(loginControllerMapper.map(request))).thenReturn(expected);

    final ResponseEntity<LoginControllerResponseInfrastructure> result =
        loginController.execute(request);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(expected.getId(), result.getBody().getId());
    assertEquals(expected.getUsername(), result.getBody().getUsername());
    assertEquals(expected.getToken(), result.getBody().getToken());
  }
}
