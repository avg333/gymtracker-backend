package org.avillar.gymtracker.authapi.register.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.authapi.common.exception.application.UserNotFoundException;
import org.avillar.gymtracker.authapi.common.exception.application.UsernameAlreadyExistsException;
import org.avillar.gymtracker.authapi.common.exception.application.WrongRegisterCodeException;
import org.avillar.gymtracker.authapi.common.facade.user.UserFacade;
import org.avillar.gymtracker.authapi.login.application.LoginService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class RegisterServiceImplTest {

  @InjectMocks private RegisterServiceImpl registerService;

  @Mock private UserFacade userFacade;
  @Mock private LoginService loginService;
  @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Test
  void shouldRegisterUserSuccessfullyAndReturnLoginData()
      throws WrongRegisterCodeException, UsernameAlreadyExistsException, UserNotFoundException {
    final UserApp requestUserApp = Instancio.create(UserApp.class);
    final String registerCode = Instancio.create(String.class);
    final String encodedPassword = Instancio.create(String.class);
    final UserApp savedUserApp = Instancio.create(UserApp.class);
    final UserApp loginResponse = Instancio.create(UserApp.class);

    ReflectionTestUtils.setField(registerService, "registerCode", registerCode);

    final ArgumentCaptor<UserApp> userAppCaptor = ArgumentCaptor.forClass(UserApp.class);

    doThrow(UserNotFoundException.class)
        .when(userFacade)
        .findByUsername(requestUserApp.getUsername());
    when(bCryptPasswordEncoder.encode(requestUserApp.getPassword())).thenReturn(encodedPassword);
    when(userFacade.saveUser(userAppCaptor.capture())).thenReturn(savedUserApp);
    when(loginService.execute(requestUserApp)).thenReturn(loginResponse);

    assertThat(registerService.execute(requestUserApp, registerCode)).isEqualTo(loginResponse);

    final UserApp userAppBeforeSave = userAppCaptor.getValue();
    assertThat(userAppBeforeSave).isNotNull();
    assertThat(userAppBeforeSave.getUsername()).isEqualTo(requestUserApp.getUsername());
    assertThat(userAppBeforeSave.getPassword()).isEqualTo(encodedPassword);

    verify(userFacade).findByUsername(requestUserApp.getUsername());
    verify(userFacade).saveUser(userAppBeforeSave);
    verify(loginService).execute(requestUserApp);
  }

  @Test
  void shouldThrowUsernameAlreadyExistsExceptionWhenUsernameIsAlreadyOnUse()
      throws UserNotFoundException {
    final UserApp userApp = Instancio.create(UserApp.class);
    final String registerCode = Instancio.create(String.class);

    ReflectionTestUtils.setField(registerService, "registerCode", registerCode);

    when(userFacade.findByUsername(userApp.getUsername()))
        .thenReturn(Instancio.create(UserApp.class));

    assertThatThrownBy(() -> registerService.execute(userApp, registerCode))
        .isInstanceOf(UsernameAlreadyExistsException.class);
  }

  @Test
  void shouldThrowWrongRegisterCodeExceptionWhenRegisterCodeIsNotValid() {
    final UserApp userApp = Instancio.create(UserApp.class);
    final String registerCode = Instancio.create(String.class);

    ReflectionTestUtils.setField(registerService, "registerCode", Instancio.create(String.class));

    assertThatThrownBy(() -> registerService.execute(userApp, registerCode))
        .isInstanceOf(WrongRegisterCodeException.class);
  }
}
