package org.avillar.gymtracker.authapi.login.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.authapi.login.application.LoginService;
import org.avillar.gymtracker.authapi.login.application.model.LoginResponseApplication;
import org.avillar.gymtracker.authapi.login.infrastructure.mapper.LoginControllerMapperImpl;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginRequest;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginResponse;
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

  @Mock private LoginService loginService;
  @Spy private LoginControllerMapperImpl loginControllerMapper;

  @Test
  void loginOk() {
    final LoginRequest request = easyRandom.nextObject(LoginRequest.class);
    final LoginResponseApplication expected = easyRandom.nextObject(LoginResponseApplication.class);

    when(loginService.execute(loginControllerMapper.map(request))).thenReturn(expected);

    final ResponseEntity<LoginResponse> result = loginController.execute(request);
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
