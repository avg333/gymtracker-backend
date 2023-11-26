package org.avillar.gymtracker.usersapi.getsettings.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class GetSettingsServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.READ;

  @InjectMocks private GetSettingsServiceImpl getSettingsService;

  @Mock private SettingsFacade settingsFacade;
  @Mock private AuthUsersService authUsersService;

  @Test
  void shouldGetUserSettingsByUserIdSuccessfully()
      throws SettingsNotFoundException, SettingsIllegalAccessException {
    final UUID userId = UUID.randomUUID();
    final Settings settings = Instancio.create(Settings.class);

    when(settingsFacade.getSettingsByUserId(userId)).thenReturn(settings);
    doNothing().when(authUsersService).checkAccess(settings, AUTH_OPERATIONS);

    assertThat(getSettingsService.execute(userId)).isEqualTo(settings);
  }

  @Test
  void shouldThrowSettingsIllegalAccessExceptionWhenUserHasNoAccess()
      throws SettingsIllegalAccessException, SettingsNotFoundException {
    final Settings settings = Instancio.create(Settings.class);
    final UUID userId = settings.getUserId();
    final SettingsIllegalAccessException ex =
        new SettingsIllegalAccessException(settings.getUserId(), AUTH_OPERATIONS, userId);

    when(settingsFacade.getSettingsByUserId(userId)).thenReturn(settings);
    doThrow(ex).when(authUsersService).checkAccess(settings, AUTH_OPERATIONS);

    assertThatThrownBy(() -> getSettingsService.execute(userId)).isEqualTo(ex);
  }

  @Test
  void shouldThrowSettingsNotFoundExceptionWhenUserHasNoSettings()
      throws SettingsNotFoundException {
    final UUID userId = UUID.randomUUID();
    final SettingsNotFoundException ex = new SettingsNotFoundException(userId);

    doThrow(ex).when(settingsFacade).getSettingsByUserId(userId);

    assertThatThrownBy(() -> getSettingsService.execute(userId)).isEqualTo(ex);
  }
}
