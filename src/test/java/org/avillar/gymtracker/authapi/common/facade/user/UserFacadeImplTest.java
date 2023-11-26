package org.avillar.gymtracker.authapi.common.facade.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.authapi.common.adapter.repository.UserDao;
import org.avillar.gymtracker.authapi.common.adapter.repository.model.UserEntity;
import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.authapi.common.exception.application.UserNotFoundException;
import org.avillar.gymtracker.authapi.common.facade.user.mapper.UserFacadeMapper;
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
class UserFacadeImplTest {

  @InjectMocks private UserFacadeImpl userFacadeImpl;

  @Mock private UserDao userDao;
  @Mock private UserFacadeMapper userFacadeMapper;

  @Test
  void shouldGetUserByUsernameSuccessfully() throws UserNotFoundException {
    final UserApp userApp = Instancio.create(UserApp.class);
    final UserEntity userEntity = Instancio.create(UserEntity.class);

    when(userDao.findByUsername(userApp.getUsername())).thenReturn(userEntity);
    when(userFacadeMapper.map(userEntity)).thenReturn(userApp);

    assertThat(userFacadeImpl.findByUsername(userApp.getUsername())).isEqualTo(userApp);
  }

  @Test
  void shouldThrowUserNotFoundExceptionWhenNotExistsUsername() {
    final String username = Instancio.create(String.class);
    final UserNotFoundException exception = new UserNotFoundException(username);

    when(userDao.findByUsername(username)).thenReturn(null);

    assertThatThrownBy(() -> userFacadeImpl.findByUsername(username)).isEqualTo(exception);
  }

  @Test
  void shouldSaveUserSuccessfully() {
    final UserApp userAppBeforeSave = Instancio.create(UserApp.class);
    final UserEntity userEntityBeforeSave = Instancio.create(UserEntity.class);
    final UserEntity userEntityAfterSave = Instancio.create(UserEntity.class);
    final UserApp userAppAfterSave = Instancio.create(UserApp.class);

    when(userFacadeMapper.map(userAppBeforeSave)).thenReturn(userEntityBeforeSave);
    when(userDao.save(userEntityBeforeSave)).thenReturn(userEntityAfterSave);
    when(userFacadeMapper.map(userEntityAfterSave)).thenReturn(userAppAfterSave);

    assertThat(userFacadeImpl.saveUser(userAppBeforeSave)).isEqualTo(userAppAfterSave);
  }
}
