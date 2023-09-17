package org.avillar.gymtracker.usersapi.getusersettings.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.usersapi.auth.application.AuthUsersService;
import org.avillar.gymtracker.usersapi.domain.Settings;
import org.avillar.gymtracker.usersapi.domain.SettingsDao;
import org.avillar.gymtracker.usersapi.getusersettings.application.mapper.GetUserSettingsApplicationMapper;
import org.avillar.gymtracker.usersapi.getusersettings.application.model.GetUserSettingsResponseApplication;
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
class GetUserSettingsServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetUserSettingsServiceImpl getUserSettingsService;

  @Mock private SettingsDao settingsDao;
  @Mock private AuthUsersService authUsersService;

  @Spy
  private GetUserSettingsApplicationMapper getUserSettingsApplicationMapper =
      Mappers.getMapper(GetUserSettingsApplicationMapper.class);

  @Test
  void executeOkTest() {
    final UUID userId = UUID.randomUUID();
    final Settings settings = easyRandom.nextObject(Settings.class);
    settings.setUserId(userId);

    when(settingsDao.findByUserId(userId)).thenReturn(List.of(settings));
    doNothing().when(authUsersService).checkAccess(settings, AuthOperations.READ);

    final GetUserSettingsResponseApplication result = getUserSettingsService.execute(userId);
    assertThat(result).isNotNull().usingRecursiveComparison().isEqualTo(settings);
  }

  @Test
  void executeNotFoundTest() {
    final UUID userId = UUID.randomUUID();

    when(settingsDao.findByUserId(userId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        assertThrows(EntityNotFoundException.class, () -> getUserSettingsService.execute(userId));
    assertThat(exception.getClassName()).isEqualTo(Settings.class.getSimpleName());
    assertThat(exception.getId()).isEqualTo(userId); // FIXME
  }

  @Test
  void executeNotAccessTest() {
    final UUID userId = UUID.randomUUID();
    final Settings settings = easyRandom.nextObject(Settings.class);
    settings.setUserId(userId);

    when(settingsDao.findByUserId(userId)).thenReturn(List.of(settings));
    doThrow(new IllegalAccessException(settings, AuthOperations.READ, userId))
        .when(authUsersService)
        .checkAccess(settings, AuthOperations.READ);

    final IllegalAccessException exception =
        assertThrows(IllegalAccessException.class, () -> getUserSettingsService.execute(userId));
    assertThat(exception.getEntityClassName()).isEqualTo(Settings.class.getSimpleName());
    assertThat(exception.getEntityId()).isEqualTo(settings.getId());
    assertThat(exception.getAuthOperations()).isEqualTo(AuthOperations.READ);
    assertThat(exception.getCurrentUserId()).isEqualTo(userId);
  }
}
