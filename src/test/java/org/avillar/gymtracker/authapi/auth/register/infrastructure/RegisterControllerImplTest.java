package org.avillar.gymtracker.authapi.auth.register.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.authapi.auth.register.application.RegisterService;
import org.avillar.gymtracker.authapi.auth.register.application.model.RegisterResponseApplication;
import org.avillar.gymtracker.authapi.auth.register.infrastructure.mapper.RegisterControllerMapperImpl;
import org.avillar.gymtracker.authapi.auth.register.infrastructure.model.RegisterRequest;
import org.avillar.gymtracker.authapi.auth.register.infrastructure.model.RegisterResponse;
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
class RegisterControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private RegisterControllerImpl registerController;

  @Mock private RegisterService registerService;
  @Spy private RegisterControllerMapperImpl registerControllerMapper;

  @Test
  void registerOk() {
    final RegisterRequest request = easyRandom.nextObject(RegisterRequest.class);
    final RegisterResponseApplication expected =
        easyRandom.nextObject(RegisterResponseApplication.class);

    when(registerService.execute(registerControllerMapper.map(request))).thenReturn(expected);

    final ResponseEntity<RegisterResponse> result = registerController.execute(request);
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
