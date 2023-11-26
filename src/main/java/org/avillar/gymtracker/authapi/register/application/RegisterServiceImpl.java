package org.avillar.gymtracker.authapi.register.application;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.authapi.common.exception.application.UserNotFoundException;
import org.avillar.gymtracker.authapi.common.exception.application.UsernameAlreadyExistsException;
import org.avillar.gymtracker.authapi.common.exception.application.WrongRegisterCodeException;
import org.avillar.gymtracker.authapi.common.facade.user.UserFacade;
import org.avillar.gymtracker.authapi.login.application.LoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

  private final UserFacade userFacade;
  private final LoginService loginService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Value("${registerCode}") // TODO Move to properties
  private String registerCode;

  @Override
  public UserApp execute(final UserApp userApp, final String registerCode)
      throws WrongRegisterCodeException, UsernameAlreadyExistsException {

    if (isRegisterCodeInvalid(registerCode)) {
      throw new WrongRegisterCodeException();
    }

    if (isUsernameInUse(userApp.getUsername())) {
      throw new UsernameAlreadyExistsException();
    }

    userFacade.saveUser(
        UserApp.builder()
            .username(userApp.getUsername())
            .password(bCryptPasswordEncoder.encode(userApp.getPassword()))
            .build());

    return loginService.execute(userApp);
  }

  private boolean isUsernameInUse(final String username) {
    try {
      return userFacade.findByUsername(username) != null;
    } catch (UserNotFoundException e) {
      return false;
    }
  }

  private boolean isRegisterCodeInvalid(final String requestRegisterCode) {
    return StringUtils.isNotEmpty(registerCode) && !registerCode.equals(requestRegisterCode);
  }
}
