package org.avillar.gymtracker.authapi.login.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.authapi.login.application.LoginService;
import org.avillar.gymtracker.authapi.login.infrastructure.mapper.LoginControllerMapper;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginRequest;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class LoginControllerImplTest {

  @InjectMocks private LoginControllerImpl loginController;

  @Mock private LoginService loginService;
  @Mock private LoginControllerMapper loginControllerMapper;

  @Test
  void shouldLoginSuccessfullyAndReturnLoginData() {
    final LoginRequest requestDto = Instancio.create(LoginRequest.class);
    final UserApp request = Instancio.create(UserApp.class);
    final UserApp response = Instancio.create(UserApp.class);
    final LoginResponse responseDto = Instancio.create(LoginResponse.class);

    when(loginControllerMapper.map(requestDto)).thenReturn(request);
    when(loginService.execute(request)).thenReturn(response);
    when(loginControllerMapper.map(response)).thenReturn(responseDto);

    assertThat(loginController.execute(requestDto)).isEqualTo(responseDto);
  }
}
