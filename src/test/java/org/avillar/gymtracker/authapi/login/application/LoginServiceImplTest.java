package org.avillar.gymtracker.authapi.login.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.authapi.login.application.mapper.LoginServiceMapper;
import org.avillar.gymtracker.authapi.login.application.model.LoginRequestApplication;
import org.avillar.gymtracker.common.auth.jwt.JwtTokenUtil;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private LoginServiceImpl loginService;

  @Mock private AuthenticationManager authenticationManager;
  @Mock private JwtTokenUtil jwtTokenUtil;

  @Spy
  private final LoginServiceMapper loginServiceMapper = Mappers.getMapper(LoginServiceMapper.class);

  void loginOk() { // TODO Finish this
    final LoginRequestApplication request = easyRandom.nextObject(LoginRequestApplication.class);
    final String jwt = easyRandom.nextObject(String.class);

    when(jwtTokenUtil.generateToken(any())).thenReturn(jwt);
  }
}
