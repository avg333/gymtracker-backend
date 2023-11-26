package org.avillar.gymtracker.usersapi.getsettings.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsIllegalAccessException;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsNotFoundException;
import org.avillar.gymtracker.usersapi.getsettings.application.GetSettingsService;
import org.avillar.gymtracker.usersapi.getsettings.infrastructure.mapper.GetSettingsInfrastructureMapper;
import org.avillar.gymtracker.usersapi.getsettings.infrastructure.model.GetSettingsResponse;
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
class GetSettingsControllerImplTest {

  @InjectMocks private GetSettingsControllerImpl getSettingsController;

  @Mock private GetSettingsService getSettingsService;
  @Mock private GetSettingsInfrastructureMapper getSettingsInfrastructureMapper;

  @Test
  void shouldGetUserSettingsByUserIdSuccessfully()
      throws SettingsNotFoundException, SettingsIllegalAccessException {
    final UUID userId = Instancio.create(UUID.class);
    final Settings serviceResponse = Instancio.create(Settings.class);
    final GetSettingsResponse mapperResponse = Instancio.create(GetSettingsResponse.class);

    when(getSettingsService.execute(userId)).thenReturn(serviceResponse);
    when(getSettingsInfrastructureMapper.map(serviceResponse)).thenReturn(mapperResponse);

    assertThat(getSettingsController.execute(userId)).isEqualTo(mapperResponse);
  }
}
