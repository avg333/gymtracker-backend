package org.avillar.gymtracker.authapi.register.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.authapi.common.exception.application.UsernameAlreadyExistsException;
import org.avillar.gymtracker.authapi.common.exception.application.WrongRegisterCodeException;
import org.avillar.gymtracker.authapi.register.application.RegisterService;
import org.avillar.gymtracker.authapi.register.infrastructure.mapper.RegisterControllerMapper;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterRequest;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterResponse;
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
class RegisterControllerImplTest {

  @InjectMocks private RegisterControllerImpl registerController;

  @Mock private RegisterService registerService;
  @Mock private RegisterControllerMapper registerControllerMapper;

  @Test
  void shouldRegisterUserSuccessfullyAndReturnLoginData()
      throws WrongRegisterCodeException, UsernameAlreadyExistsException {
    final RegisterRequest requestDto = Instancio.create(RegisterRequest.class);
    final UserApp request = Instancio.create(UserApp.class);
    final UserApp response = Instancio.create(UserApp.class);
    final RegisterResponse responseDto = Instancio.create(RegisterResponse.class);

    when(registerControllerMapper.map(requestDto)).thenReturn(request);
    when(registerService.execute(request, requestDto.registerCode())).thenReturn(response);
    when(registerControllerMapper.map(response)).thenReturn(responseDto);

    assertThat(registerController.execute(requestDto)).isEqualTo(responseDto);
  }
}
