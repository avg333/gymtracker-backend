package org.avillar.gymtracker.usersapi.getusersettings.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.usersapi.getusersettings.application.GetUserSettingsService;
import org.avillar.gymtracker.usersapi.getusersettings.application.model.GetUserSettingsResponseApplication;
import org.avillar.gymtracker.usersapi.getusersettings.infrastructure.mapper.GetUserSettingsInfrastructureMapper;
import org.avillar.gymtracker.usersapi.getusersettings.infrastructure.model.GetUserSettingsResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class GetUserSettingsControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetUserSettingsControllerImpl getUserSettingsController;

  @Mock private GetUserSettingsService getUserSettingsService;

  @Spy
  private final GetUserSettingsInfrastructureMapper getUserSettingsInfrastructureMapper =
      Mappers.getMapper(GetUserSettingsInfrastructureMapper.class);

  @Test
  void executeTest() {
    final UUID userId = easyRandom.nextObject(UUID.class);
    final GetUserSettingsResponseApplication serviceResult =
        easyRandom.nextObject(GetUserSettingsResponseApplication.class);

    when(getUserSettingsService.execute(userId)).thenReturn(serviceResult);

    final GetUserSettingsResponseInfrastructure result = getUserSettingsController.execute(userId);
    assertThat(result).isNotNull().usingRecursiveComparison().isEqualTo(serviceResult);
  }
}
