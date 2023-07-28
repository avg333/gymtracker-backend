package org.avillar.gymtracker.authapi.register.application;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.authapi.domain.UserDao;
import org.avillar.gymtracker.authapi.login.application.LoginService;
import org.avillar.gymtracker.authapi.register.application.mapper.RegisterServiceMapper;
import org.avillar.gymtracker.authapi.register.application.model.RegisterRequestApplication;
import org.avillar.gymtracker.authapi.register.application.model.RegisterResponseApplication;
import org.avillar.gymtracker.common.errors.application.exceptions.RegisterExcepcion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

  private final UserDao userDao;
  private final LoginService loginService;
  private final RegisterServiceMapper registerServiceMapper;

  @Value("${registerCode}")
  private String registerCode;

  @Override
  public RegisterResponseApplication execute(
      final RegisterRequestApplication registerRequestApplication) {

    if (isRegisterCodeInvalid(registerRequestApplication)) {
      throw new RegisterExcepcion("Wrong auth code!");
    }

    if (userDao.findByUsername(registerRequestApplication.getUsername()) != null) {
      throw new RegisterExcepcion("Username already exists");
    }

    createUser(registerRequestApplication);

    return registerServiceMapper.map(
        loginService.execute(registerServiceMapper.map(registerRequestApplication)));
  }

  private boolean isRegisterCodeInvalid(RegisterRequestApplication registerRequestApplication) {
    return StringUtils.isNotEmpty(registerCode)
        && !registerCode.equals(registerRequestApplication.getRegisterCode());
  }

  private void createUser(final RegisterRequestApplication registerRequestApplication) {
    final UserApp userApp = new UserApp();
    userApp.setUsername(registerRequestApplication.getUsername());
    userApp.setPassword(
        new BCryptPasswordEncoder().encode(registerRequestApplication.getPassword()));
    userDao.save(userApp);
  }
}
