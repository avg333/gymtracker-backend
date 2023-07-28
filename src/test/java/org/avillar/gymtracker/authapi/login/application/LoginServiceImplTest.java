package org.avillar.gymtracker.authapi.login.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.UUID;
import org.avillar.gymtracker.authapi.login.application.mapper.LoginServiceMapper;
import org.avillar.gymtracker.authapi.login.application.model.LoginRequestApplication;
import org.avillar.gymtracker.authapi.login.application.model.LoginResponseApplication;
import org.avillar.gymtracker.common.auth.jwt.JwtTokenUtil;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private LoginServiceImpl loginService;

  @Mock private AuthenticationManager authenticationManager;
  @Mock private JwtTokenUtil jwtTokenUtil;

  @Spy
  private final LoginServiceMapper loginServiceMapper = Mappers.getMapper(LoginServiceMapper.class);

  @Test
  void loginOk() {
    final LoginRequestApplication request = easyRandom.nextObject(LoginRequestApplication.class);
    final UUID userId = UUID.randomUUID();
    final String jwt = easyRandom.nextObject(String.class);
    final String tokenType = easyRandom.nextObject(String.class);

    when(authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())))
        .thenReturn(
            new UsernamePasswordAuthenticationToken(
                new UserDetailsImpl(
                    userId, request.getUsername(), request.getPassword(), new ArrayList<>()),
                null));
    when(jwtTokenUtil.generateToken(any())).thenReturn(jwt);
    when(jwtTokenUtil.getTokenType()).thenReturn(tokenType);

    final LoginResponseApplication response = loginService.execute(request);
    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo(userId);
    assertThat(response.getUsername()).isEqualTo(request.getUsername());
    assertThat(response.getToken()).isEqualTo(jwt);
    assertThat(response.getType()).isEqualTo(tokenType);
  }
}
