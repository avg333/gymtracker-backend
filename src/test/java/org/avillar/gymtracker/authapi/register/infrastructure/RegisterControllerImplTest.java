package org.avillar.gymtracker.authapi.register.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.authapi.register.application.RegisterService;
import org.avillar.gymtracker.authapi.register.application.model.RegisterResponseApplication;
import org.avillar.gymtracker.authapi.register.infrastructure.mapper.RegisterControllerMapper;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterRequest;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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

    when(registerService.execute(registerControllerMapper.map(request))).thenReturn(expected);

    assertThat(registerController.execute(request)).usingRecursiveComparison().isEqualTo(expected);
  }
}
