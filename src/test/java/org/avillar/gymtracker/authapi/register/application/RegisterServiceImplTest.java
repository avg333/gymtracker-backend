package org.avillar.gymtracker.authapi.register.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.authapi.domain.UserDao;
import org.avillar.gymtracker.authapi.login.application.LoginService;
import org.avillar.gymtracker.authapi.login.application.model.LoginResponseApplication;
import org.avillar.gymtracker.authapi.register.application.mapper.RegisterServiceMapperImpl;
import org.avillar.gymtracker.authapi.register.application.model.RegisterRequestApplication;
import org.avillar.gymtracker.authapi.register.application.model.RegisterResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class RegisterServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private RegisterServiceImpl registerService;

  @Mock private UserDao userDao;
  @Mock private LoginService loginService;
  @Spy private RegisterServiceMapperImpl registerServiceMapper;

  @Test
  void registerOk() {
    final LoginResponseApplication expected = easyRandom.nextObject(LoginResponseApplication.class);
    final RegisterRequestApplication request =
        easyRandom.nextObject(RegisterRequestApplication.class);
    request.setUsername(expected.getUsername());
    ReflectionTestUtils.setField(registerService, "registerCode", request.getRegisterCode());

    when(userDao.findByUsername(request.getUsername())).thenReturn(null);
    when(userDao.save(any(UserApp.class))).thenAnswer(i -> i.getArguments()[0]);
    when(loginService.execute(registerServiceMapper.map(request))).thenReturn(expected);

    final RegisterResponseApplication result = registerService.execute(request);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    verify(userDao).save(any(UserApp.class));
  }

  @Test
  void registerInvalidCode() {
    ReflectionTestUtils.setField(
        registerService, "registerCode", easyRandom.nextObject(String.class));

    final AuthenticationException exception =
        assertThrows(
            AuthenticationException.class,
            () -> registerService.execute(easyRandom.nextObject(RegisterRequestApplication.class)));
    assertEquals("Wrong auth code!", exception.getMessage());
  }

  @Test
  void registerUsernameAlreadyExists() {
    final RegisterRequestApplication registerRequestApplication =
        easyRandom.nextObject(RegisterRequestApplication.class);
    ReflectionTestUtils.setField(
        registerService, "registerCode", registerRequestApplication.getRegisterCode());

    when(userDao.findByUsername(registerRequestApplication.getUsername()))
        .thenReturn(easyRandom.nextObject(UserApp.class));

    final AuthenticationException exception =
        assertThrows(
            AuthenticationException.class,
            () -> registerService.execute(registerRequestApplication));
    assertEquals("Username already exists", exception.getMessage());
  }
}
