package org.avillar.gymtracker.usersapi.modifyusersettings.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.usersapi.modifyusersettings.application.ModifyUserSettingsService;
import org.avillar.gymtracker.usersapi.modifyusersettings.application.model.ModifyUserSettingsRequestApplication;
import org.avillar.gymtracker.usersapi.modifyusersettings.application.model.ModifyUserSettingsResponseApplication;
import org.avillar.gymtracker.usersapi.modifyusersettings.infrastructure.mapper.ModifyUserSettingsInfrastructureMapper;
import org.avillar.gymtracker.usersapi.modifyusersettings.infrastructure.model.ModifyUserSettingsRequestInfrastructure;
import org.avillar.gymtracker.usersapi.modifyusersettings.infrastructure.model.ModifyUserSettingsResponseInfrastructure;
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
class ModifyUserSettingsControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private ModifyUserSettingsControllerImpl modifyUserSettingsController;

  @Mock private ModifyUserSettingsService modifyUserSettingsService;

  @Spy
  private final ModifyUserSettingsInfrastructureMapper modifyUserSettingsInfrastructureMapper =
      Mappers.getMapper(ModifyUserSettingsInfrastructureMapper.class);

  @Test
  void executeTest() {
    final UUID userId = easyRandom.nextObject(UUID.class);
    final ModifyUserSettingsRequestInfrastructure request =
        easyRandom.nextObject(ModifyUserSettingsRequestInfrastructure.class);
    final ModifyUserSettingsResponseApplication serviceResult =
        easyRandom.nextObject(ModifyUserSettingsResponseApplication.class);

    final ArgumentCaptor<ModifyUserSettingsRequestApplication>
        modifyUserSettingsRequestApplicationArgumentCaptor =
            ArgumentCaptor.forClass(ModifyUserSettingsRequestApplication.class);
    when(modifyUserSettingsService.execute(
            eq(userId), modifyUserSettingsRequestApplicationArgumentCaptor.capture()))
        .thenReturn(serviceResult);

    final ModifyUserSettingsResponseInfrastructure result =
        modifyUserSettingsController.execute(userId, request);
    assertThat(result).isNotNull().usingRecursiveComparison().isEqualTo(serviceResult);

    assertThat(modifyUserSettingsRequestApplicationArgumentCaptor.getValue())
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(request);
  }
}
