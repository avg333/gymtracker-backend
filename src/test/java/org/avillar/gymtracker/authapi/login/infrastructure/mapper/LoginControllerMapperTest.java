package org.avillar.gymtracker.authapi.login.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginRequest;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class LoginControllerMapperTest {

  private final LoginControllerMapper mapper = Mappers.getMapper(LoginControllerMapper.class);

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldMapUserAppToLoginResponse(final boolean hasToken) {
    final UserApp source = Instancio.create(UserApp.class);
    if (!hasToken) {
      source.setToken(null);
    }

    final LoginResponse result = mapper.map(source);
    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(source.getId());
    assertThat(result.username()).isEqualTo(source.getUsername());

    if (source.getToken() == null) {
      assertThat(result.token()).isNull();
      assertThat(result.type()).isNull();
    } else {
      assertThat(result.type()).isEqualTo(source.getToken().getType());
      assertThat(result.token()).isEqualTo(source.getToken().getValue());
    }
  }

  @Test
  void shouldReturnNullWhenUserAppIsNull() {
    final UserApp source = null;
    assertThat(mapper.map(source)).isNull();
  }

  @Test
  void shouldMapLoginRequestToUserApp() {
    final LoginRequest source = Instancio.create(LoginRequest.class);

    final UserApp result = mapper.map(source);
    assertThat(result).isNotNull();
    assertThat(result.getUsername()).isEqualTo(source.username());
    assertThat(result.getPassword()).isEqualTo(source.password());
  }

  @Test
  void shouldReturnNullWhenLoginRequestIsNull() {
    final LoginRequest source = null;
    assertThat(mapper.map(source)).isNull();
  }
}
