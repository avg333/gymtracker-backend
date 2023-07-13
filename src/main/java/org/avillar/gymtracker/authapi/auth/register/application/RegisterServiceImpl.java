package org.avillar.gymtracker.authapi.auth.register.application;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.authapi.auth.register.application.mapper.RegisterServiceMapper;
import org.avillar.gymtracker.authapi.auth.register.application.model.RegisterRequestApplication;
import org.avillar.gymtracker.authapi.auth.register.application.model.RegisterResponseApplication;
import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.authapi.domain.UserApp.ActivityLevelEnum;
import org.avillar.gymtracker.authapi.domain.UserApp.GenderEnum;
import org.avillar.gymtracker.authapi.domain.UserDao;
import org.avillar.gymtracker.common.auth.jwt.JwtTokenUtil;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

  private final UserDao userDao;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final RegisterServiceMapper registerServiceMapper;

  @Value("${registerCode}")
  private String registerCode;

  @Override
  public RegisterResponseApplication execute(
      final RegisterRequestApplication registerRequestApplication) {
    if (StringUtils.isNotEmpty(registerCode)
        && registerCode.equals(registerRequestApplication.getRegisterCode())) {
      throw new AuthenticationException("Wrong auth code!") {}; // TODO Improve this
    }

    if (userDao.findByUsername(registerRequestApplication.getUsername()) != null) {
      throw new AuthenticationException("User already exists") {}; // TODO Improve this
    }

    final UserApp userApp = new UserApp();
    userApp.setUsername(registerRequestApplication.getUsername());
    userApp.setPassword(
        new BCryptPasswordEncoder().encode(registerRequestApplication.getPassword()));
    userApp.setEmail("");
    userApp.setName("");
    userApp.setLastNameFirst("");
    userApp.setLastNameSecond("");
    userApp.setBirth(new Date());
    userApp.setGender(GenderEnum.MALE);
    userApp.setActivityLevel(ActivityLevelEnum.MODERATE);

    userDao.save(userApp);

    final Authentication auth =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                registerRequestApplication.getUsername(),
                registerRequestApplication.getPassword()));

    final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    final String jwt = jwtTokenUtil.generateToken(userDetails);

    final RegisterResponseApplication registerResponseApplication =
        registerServiceMapper.map(userDetails);
    registerResponseApplication.setToken(jwt);

    return registerResponseApplication;
  }
}
