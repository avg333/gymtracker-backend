package org.avillar.gymtracker.usersapi.modifyusersettings.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.usersapi.auth.application.AuthUsersService;
import org.avillar.gymtracker.usersapi.domain.Settings;
import org.avillar.gymtracker.usersapi.domain.SettingsDao;
import org.avillar.gymtracker.usersapi.modifyusersettings.application.mapper.ModifyUserSettingsApplicationMapper;
import org.avillar.gymtracker.usersapi.modifyusersettings.application.model.ModifyUserSettingsRequestApplication;
import org.avillar.gymtracker.usersapi.modifyusersettings.application.model.ModifyUserSettingsResponseApplication;
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
class ModifyUserSettingsServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private ModifyUserSettingsServiceImpl modifyUserSettingsService;

  @Mock private SettingsDao settingsDao;
  @Mock private AuthUsersService authUsersService;

  @Spy
  private ModifyUserSettingsApplicationMapper modifyUserSettingsApplicationMapper =
      Mappers.getMapper(ModifyUserSettingsApplicationMapper.class);

  @Test
  void executeFirstTest() {
    final UUID userId = UUID.randomUUID();
    final ModifyUserSettingsRequestApplication request =
        easyRandom.nextObject(ModifyUserSettingsRequestApplication.class);

    final ArgumentCaptor<Settings> settingsArgumentCaptor = ArgumentCaptor.forClass(Settings.class);
    doNothing()
        .when(authUsersService)
        .checkAccess(settingsArgumentCaptor.capture(), eq(AuthOperations.CREATE));

    when(settingsDao.findByUserId(userId)).thenReturn(Collections.emptyList());

    final ArgumentCaptor<Settings> settingsArgumentCaptorTwo =
        ArgumentCaptor.forClass(Settings.class);
    when(settingsDao.save(settingsArgumentCaptorTwo.capture())).thenReturn(null);

    final ModifyUserSettingsResponseApplication result =
        modifyUserSettingsService.execute(userId, request);
    assertThat(result).isNotNull().usingRecursiveComparison().isEqualTo(request);

    final Settings settingsFirst = settingsArgumentCaptor.getValue();
    assertThat(settingsFirst.getSelectedBar()).isEqualTo(request.getSelectedBar());
    assertThat(settingsFirst.getSelectedIncrement()).isEqualTo(request.getSelectedIncrement());
    assertThat(settingsFirst.getSelectedPlates())
        .usingRecursiveComparison()
        .isEqualTo(request.getSelectedPlates());
    assertThat(settingsFirst.getUserId()).isEqualTo(userId);

    final Settings settingsSecond = settingsArgumentCaptorTwo.getValue();
    assertThat(settingsSecond.getId()).isNull();

    verify(settingsDao).save(settingsSecond);
  }

  @Test
  void executeNotFirstTest() {
    final UUID userId = UUID.randomUUID();
    final ModifyUserSettingsRequestApplication request =
        easyRandom.nextObject(ModifyUserSettingsRequestApplication.class);
    final Settings settings = easyRandom.nextObject(Settings.class);

    final ArgumentCaptor<Settings> settingsArgumentCaptor = ArgumentCaptor.forClass(Settings.class);
    doNothing()
        .when(authUsersService)
        .checkAccess(settingsArgumentCaptor.capture(), eq(AuthOperations.CREATE));

    when(settingsDao.findByUserId(userId)).thenReturn(List.of(settings));

    final ArgumentCaptor<Settings> settingsArgumentCaptorTwo =
        ArgumentCaptor.forClass(Settings.class);
    when(settingsDao.save(settingsArgumentCaptorTwo.capture())).thenReturn(null);

    final ModifyUserSettingsResponseApplication result =
        modifyUserSettingsService.execute(userId, request);
    assertThat(result).isNotNull().usingRecursiveComparison().isEqualTo(request);

    final Settings settingsFirst = settingsArgumentCaptor.getValue();
    assertThat(settingsFirst.getSelectedBar()).isEqualTo(request.getSelectedBar());
    assertThat(settingsFirst.getSelectedIncrement()).isEqualTo(request.getSelectedIncrement());
    assertThat(settingsFirst.getSelectedPlates())
        .usingRecursiveComparison()
        .isEqualTo(request.getSelectedPlates());
    assertThat(settingsFirst.getUserId()).isEqualTo(userId);

    final Settings settingsSecond = settingsArgumentCaptorTwo.getValue();
    assertThat(settingsSecond.getId()).isEqualTo(settings.getId());

    verify(settingsDao).save(settingsSecond);
  }

  @Test
  void executeNotAccessTest() {
    final UUID userId = UUID.randomUUID();
    final ModifyUserSettingsRequestApplication request =
        easyRandom.nextObject(ModifyUserSettingsRequestApplication.class);

    final ArgumentCaptor<Settings> settingsArgumentCaptor = ArgumentCaptor.forClass(Settings.class);
    doThrow(
            new IllegalAccessException(
                easyRandom.nextObject(Settings.class), AuthOperations.CREATE, userId))
        .when(authUsersService)
        .checkAccess(settingsArgumentCaptor.capture(), eq(AuthOperations.CREATE));

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class, () -> modifyUserSettingsService.execute(userId, request));

    final Settings settingsFirst = settingsArgumentCaptor.getValue();
    assertThat(settingsFirst.getSelectedBar()).isEqualTo(request.getSelectedBar());
    assertThat(settingsFirst.getSelectedIncrement()).isEqualTo(request.getSelectedIncrement());
    assertThat(settingsFirst.getSelectedPlates())
        .usingRecursiveComparison()
        .isEqualTo(request.getSelectedPlates());
    assertThat(settingsFirst.getUserId()).isEqualTo(userId);

    assertThat(exception.getEntityClassName()).isEqualTo(settingsFirst.getClass().getSimpleName());
    assertThat(exception.getAuthOperations()).isEqualTo(AuthOperations.CREATE);
    assertThat(exception.getCurrentUserId()).isEqualTo(settingsFirst.getUserId());

    verify(settingsDao, never()).save(any());
  }
}
