package org.avillar.gymtracker.usersapi.modifysettings.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.usersapi.common.auth.application.AuthUsersService;
import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsIllegalAccessException;
import org.avillar.gymtracker.usersapi.common.exception.application.SettingsNotFoundException;
import org.avillar.gymtracker.usersapi.common.facade.settings.SettingsFacade;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class ModifySettingsServiceImplTest {

  @InjectMocks private ModifySettingsServiceImpl modifySettingsService;

  @Mock private SettingsFacade settingsFacade;
  @Mock private AuthUsersService authUsersService;

  @Test
  void shouldModifyAndReturnSettingsSuccessfully()
      throws SettingsIllegalAccessException, SettingsNotFoundException {
    final UUID userId = UUID.randomUUID();
    final Settings settingsFromRequest = Instancio.create(Settings.class);
    final Settings settingsSaved = null;
    final Settings settingsBeforeSave = Instancio.create(Settings.class);
    settingsFromRequest.setId(null);
    settingsFromRequest.setUserId(null);

    final ArgumentCaptor<Settings> settingsArgumentCaptor = ArgumentCaptor.forClass(Settings.class);

    when(settingsFacade.getSettingsByUserId(userId)).thenReturn(settingsSaved);
    doNothing()
        .when(authUsersService)
        .checkAccess(settingsArgumentCaptor.capture(), eq(AuthOperations.CREATE));
    when(settingsFacade.saveSettings(settingsFromRequest)).thenReturn(settingsBeforeSave);

    assertThat(modifySettingsService.execute(userId, settingsFromRequest))
        .isEqualTo(settingsBeforeSave);

    final Settings settingsFirst = settingsArgumentCaptor.getValue();
    assertThat(settingsFirst)
        .isNotNull()
        .usingRecursiveComparison()
        .ignoringFields("id", "userId")
        .isEqualTo(settingsFromRequest);
    assertThat(settingsFirst.getId()).isNull();
    assertThat(settingsFirst.getUserId()).isEqualTo(userId);
    assertThat(settingsFirst.getSelectedPlates()).isSorted();

    verify(settingsFacade).saveSettings(settingsFromRequest);
  }

  @Test
  void shouldModifyAndReturnUserSettingsByUserIdSuccessfully()
      throws SettingsIllegalAccessException, SettingsNotFoundException {
    final UUID userId = UUID.randomUUID();
    final Settings settingsFromRequest = Instancio.create(Settings.class);
    final Settings settingsSaved = Instancio.create(Settings.class);
    final Settings settingsBeforeSave = Instancio.create(Settings.class);
    settingsFromRequest.setId(null);
    settingsFromRequest.setUserId(null);

    final ArgumentCaptor<Settings> settingsArgumentCaptor = ArgumentCaptor.forClass(Settings.class);

    when(settingsFacade.getSettingsByUserId(userId)).thenReturn(settingsSaved);
    doNothing()
        .when(authUsersService)
        .checkAccess(settingsArgumentCaptor.capture(), eq(AuthOperations.UPDATE));
    when(settingsFacade.saveSettings(settingsFromRequest)).thenReturn(settingsBeforeSave);

    assertThat(modifySettingsService.execute(userId, settingsFromRequest))
        .isEqualTo(settingsBeforeSave);

    final Settings settingsFirst = settingsArgumentCaptor.getValue();
    assertThat(settingsFirst)
        .isNotNull()
        .usingRecursiveComparison()
        .ignoringFields("id", "userId")
        .isEqualTo(settingsFromRequest);
    assertThat(settingsFirst.getId()).isEqualTo(settingsSaved.getId());
    assertThat(settingsFirst.getUserId()).isEqualTo(userId);
    assertThat(settingsFirst.getSelectedPlates()).isSorted();

    verify(settingsFacade).saveSettings(settingsFromRequest);
  }

  @Test
  void shouldThrowSettingsIllegalAccessExceptionCreatingSettingsWhenUserHasNoAccess()
      throws SettingsIllegalAccessException, SettingsNotFoundException {
    final UUID userId = UUID.randomUUID();
    final Settings settingsRequest = Instancio.create(Settings.class);
    final SettingsIllegalAccessException ex =
        new SettingsIllegalAccessException(null, AuthOperations.CREATE, userId);

    when(settingsFacade.getSettingsByUserId(userId)).thenReturn(null);
    doThrow(ex).when(authUsersService).checkAccess(settingsRequest, AuthOperations.CREATE);

    assertThatThrownBy(() -> modifySettingsService.execute(userId, settingsRequest)).isEqualTo(ex);

    verify(settingsFacade, never()).saveSettings(any());
  }

  @Test
  void shouldThrowSettingsIllegalAccessExceptionModifyingSettingsWhenUserHasNoAccess()
      throws SettingsIllegalAccessException, SettingsNotFoundException {
    final UUID userId = UUID.randomUUID();
    final Settings settingsRequest = Instancio.create(Settings.class);
    final SettingsIllegalAccessException ex =
        new SettingsIllegalAccessException(
            settingsRequest.getUserId(), AuthOperations.UPDATE, userId);

    when(settingsFacade.getSettingsByUserId(userId)).thenReturn(settingsRequest);
    doThrow(ex).when(authUsersService).checkAccess(settingsRequest, AuthOperations.UPDATE);

    assertThatThrownBy(() -> modifySettingsService.execute(userId, settingsRequest)).isEqualTo(ex);

    verify(settingsFacade, never()).saveSettings(any());
  }
}
