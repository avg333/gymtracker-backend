package org.avillar.gymtracker.authapi.common.facade.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.authapi.common.adapter.repository.UserDao;
import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.authapi.common.exception.application.UserNotFoundException;
import org.avillar.gymtracker.authapi.common.facade.user.mapper.UserFacadeMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

  private final UserDao userDao;
  private final UserFacadeMapper userFacadeMapper;

  @Override
  public UserApp findByUsername(final String username) throws UserNotFoundException {
    return userFacadeMapper.map(
        Optional.ofNullable(userDao.findByUsername(username))
            .orElseThrow(() -> new UserNotFoundException(username)));
  }

  @Override
  public UserApp saveUser(final UserApp userApp) {
    return userFacadeMapper.map(userDao.save(userFacadeMapper.map(userApp)));
  }
}
