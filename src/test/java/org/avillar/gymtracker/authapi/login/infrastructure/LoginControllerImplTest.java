package org.avillar.gymtracker.authapi.login.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.authapi.login.application.LoginService;
import org.avillar.gymtracker.authapi.login.application.model.LoginRequestApplication;
import org.avillar.gymtracker.authapi.login.application.model.LoginResponseApplication;
import org.avillar.gymtracker.authapi.login.infrastructure.mapper.LoginControllerMapper;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginRequest;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class LoginControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private LoginControllerImpl loginController;

  @Mock private LoginService loginService;

  @Spy
  private final LoginControllerMapper loginControllerMapper =
      Mappers.getMapper(LoginControllerMapper.class);

  @Test
  void loginOk() {
    final LoginRequest request = easyRandom.nextObject(LoginRequest.class);
    final LoginResponseApplication expected = easyRandom.nextObject(LoginResponseApplication.class);

    final ArgumentCaptor<LoginRequestApplication> loginRequestApplicationCaptor =
        ArgumentCaptor.forClass(LoginRequestApplication.class);

    when(loginService.execute(loginRequestApplicationCaptor.capture())).thenReturn(expected);

    assertThat(loginController.execute(request)).usingRecursiveComparison().isEqualTo(expected);
    assertThat(loginRequestApplicationCaptor.getValue())
        .usingRecursiveComparison()
        .isEqualTo(request);
    // TODO Fix types
  }
}
