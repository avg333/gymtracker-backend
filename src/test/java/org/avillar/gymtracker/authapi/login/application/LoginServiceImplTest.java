package org.avillar.gymtracker.authapi.login.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.UUID;
import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.authapi.login.application.mapper.LoginServiceMapper;
import org.avillar.gymtracker.common.auth.jwt.JwtTokenUtil;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

  @InjectMocks private LoginServiceImpl loginService;

  @Mock private AuthenticationManager authenticationManager;
  @Mock private JwtTokenUtil jwtTokenUtil;
  @Mock private LoginServiceMapper loginServiceMapper;

  @Test
  void shouldLoginSuccessfullyAndReturnLoginData() {
    final UserApp request = Instancio.create(UserApp.class);
    final UsernamePasswordAuthenticationToken authenticationToken =
        Instancio.create(UsernamePasswordAuthenticationToken.class);
    final UserDetailsImpl principal =
        new UserDetailsImpl(
            UUID.randomUUID(), request.getUsername(), request.getPassword(), new ArrayList<>());
    final String jwt = Instancio.create(String.class);
    final String tokenType = Instancio.create(String.class);
    final UserApp response = Instancio.create(UserApp.class);

    when(loginServiceMapper.map(request)).thenReturn(authenticationToken);
    when(authenticationManager.authenticate(authenticationToken))
        .thenReturn(new UsernamePasswordAuthenticationToken(principal, null));
    when(jwtTokenUtil.generateToken(principal)).thenReturn(jwt);
    when(jwtTokenUtil.getTokenType()).thenReturn(tokenType);
    when(loginServiceMapper.map(principal)).thenReturn(response);

    final UserApp result = loginService.execute(request);
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(response.getId());
    assertThat(result.getUsername()).isEqualTo(response.getUsername());
    assertThat(result.getPassword()).isEqualTo(response.getPassword());
    assertThat(result.getToken()).isNotNull();
    assertThat(result.getToken().getValue()).isEqualTo(jwt);
    assertThat(result.getToken().getType()).isEqualTo(tokenType);
  }
}
