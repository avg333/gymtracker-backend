package org.avillar.gymtracker.authapi.register.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.authapi.register.application.RegisterService;
import org.avillar.gymtracker.authapi.register.application.model.RegisterRequestApplication;
import org.avillar.gymtracker.authapi.register.application.model.RegisterResponseApplication;
import org.avillar.gymtracker.authapi.register.infrastructure.mapper.RegisterControllerMapper;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterRequest;
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
class RegisterControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private RegisterControllerImpl registerController;

  @Mock private RegisterService registerService;

  @Spy
  private final RegisterControllerMapper registerControllerMapper =
      Mappers.getMapper(RegisterControllerMapper.class);

  @Test
  void registerOk() {
    final RegisterRequest request = easyRandom.nextObject(RegisterRequest.class);
    final RegisterResponseApplication expected =
        easyRandom.nextObject(RegisterResponseApplication.class);

    final ArgumentCaptor<RegisterRequestApplication> registerRequestApplicationCaptor =
        ArgumentCaptor.forClass(RegisterRequestApplication.class);

    when(registerService.execute(registerRequestApplicationCaptor.capture())).thenReturn(expected);

    assertThat(registerController.execute(request)).usingRecursiveComparison().isEqualTo(expected);
    assertThat(registerRequestApplicationCaptor.getValue())
        .usingRecursiveComparison()
        .isEqualTo(request);
  }
}
