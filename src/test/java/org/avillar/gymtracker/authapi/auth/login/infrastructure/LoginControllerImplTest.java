package org.avillar.gymtracker.authapi.auth.login.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.authapi.auth.login.application.AuthService;
import org.avillar.gymtracker.authapi.auth.login.application.model.LoginControllerResponseApplication;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.mapper.LoginControllerMapperImpl;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginControllerRequest;
import org.avillar.gymtracker.authapi.auth.login.infrastructure.model.LoginControllerResponse;
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
    final LoginControllerRequest request = easyRandom.nextObject(LoginControllerRequest.class);
    final LoginControllerResponseApplication expected =
        easyRandom.nextObject(LoginControllerResponseApplication.class);

    when(authService.execute(loginControllerMapper.map(request))).thenReturn(expected);

    final ResponseEntity<LoginControllerResponse> result = loginController.execute(request);
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
