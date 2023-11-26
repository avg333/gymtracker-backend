package org.avillar.gymtracker.authapi.login.application.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Execution(ExecutionMode.CONCURRENT)
class LoginServiceMapperTest {

  private final LoginServiceMapper mapper = Mappers.getMapper(LoginServiceMapper.class);

  @Test
  void shouldMapUserDetailsImplToUserApp() {
    final UserDetailsImpl source = Instancio.create(UserDetailsImpl.class);

    final UserApp result = mapper.map(source);
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(source.getId());
    assertThat(result.getUsername()).isEqualTo(source.getUsername());
    assertThat(result.getPassword()).isEqualTo(source.getPassword());
    assertThat(result.getToken()).isNull();
  }

  @Test
  void shouldReturnNullWhenUserDetailsImplIsNull() {
    final UserDetailsImpl source = null;
    assertThat(mapper.map(source)).isNull();
  }

  @Test
  void shouldMapUserAppToUsernamePasswordAuthenticationToken() {
    final UserApp source = Instancio.create(UserApp.class);

    final UsernamePasswordAuthenticationToken result = mapper.map(source);
    assertThat(result).isNotNull();
    assertThat(result.getPrincipal()).isEqualTo(source.getUsername());
    assertThat(result.getCredentials()).isEqualTo(source.getPassword());
  }

  @Test
  void shouldReturnNullWhenUserAppIsNull() {
    final UserApp source = null;
    assertThat(mapper.map(source)).isNull();
  }
}
