package org.avillar.gymtracker.authapi.register.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterRequest;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class RegisterControllerMapperTest {

  private final RegisterControllerMapper mapper = Mappers.getMapper(RegisterControllerMapper.class);

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldMapUserAppToRegisterResponse(final boolean hasToken) {
    final UserApp source = Instancio.create(UserApp.class);
    if (!hasToken) {
      source.setToken(null);
    }

    final RegisterResponse result = mapper.map(source);
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
  void shouldMapRegisterRequestToUserApp() {
    final RegisterRequest source = Instancio.create(RegisterRequest.class);

    final UserApp result = mapper.map(source);
    assertThat(result).isNotNull();
    assertThat(result.getUsername()).isEqualTo(source.username());
    assertThat(result.getPassword()).isEqualTo(source.password());
  }

  @Test
  void shouldReturnNullWhenRegisterRequestIsNull() {
    final RegisterRequest source = null;
    assertThat(mapper.map(source)).isNull();
  }
}
