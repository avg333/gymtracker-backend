package org.avillar.gymtracker.usersapi.modifysettings.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsIllegalAccessException;
import org.avillar.gymtracker.usersapi.modifysettings.application.ModifySettingsService;
import org.avillar.gymtracker.usersapi.modifysettings.infrastructure.mapper.ModifySettingsInfrastructureMapper;
import org.avillar.gymtracker.usersapi.modifysettings.infrastructure.model.ModifySettingsRequestDto;
import org.avillar.gymtracker.usersapi.modifysettings.infrastructure.model.ModifySettingsResponseDto;
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
class ModifySettingsControllerImplTest {

  @InjectMocks private ModifySettingsControllerImpl modifySettingsController;

  @Mock private ModifySettingsService modifySettingsService;
  @Mock private ModifySettingsInfrastructureMapper modifySettingsInfrastructureMapper;

  @Test
  void shouldModifyAndReturnSettingsSuccessfully() throws SettingsIllegalAccessException {
    final UUID userId = Instancio.create(UUID.class);
    final ModifySettingsRequestDto requestDto = Instancio.create(ModifySettingsRequestDto.class);
    final Settings request = Instancio.create(Settings.class);
    final Settings response = Instancio.create(Settings.class);
    final ModifySettingsResponseDto responseDto = Instancio.create(ModifySettingsResponseDto.class);

    when(modifySettingsInfrastructureMapper.map(requestDto)).thenReturn(request);
    when(modifySettingsService.execute(userId, request)).thenReturn(response);
    when(modifySettingsInfrastructureMapper.map(response)).thenReturn(responseDto);

    assertThat(modifySettingsController.execute(userId, requestDto)).isEqualTo(responseDto);
  }
}
