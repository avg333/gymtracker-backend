package org.avillar.gymtracker.authapi.common.facade.user.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.authapi.common.adapter.repository.model.UserEntity;
import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class UserFacadeMapperTest {

  private final UserFacadeMapper mapper = Mappers.getMapper(UserFacadeMapper.class);

  @Test
  void shouldMapUserEntityToUserApp() {
    final UserEntity source = Instancio.create(UserEntity.class);

    final UserApp result = mapper.map(source);
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(source.getId());
    assertThat(result.getUsername()).isEqualTo(source.getUsername());
    assertThat(result.getPassword()).isEqualTo(source.getPassword());
  }

  @Test
  void shouldReturnNullWhenUserEntityIsNull() {
    final UserEntity source = null;
    assertThat(mapper.map(source)).isNull();
  }

  @Test
  void shouldMapUserAppToUserEntity() {
    final UserApp source = Instancio.create(UserApp.class);

    final UserEntity result = mapper.map(source);
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(source.getId());
    assertThat(result.getUsername()).isEqualTo(source.getUsername());
    assertThat(result.getPassword()).isEqualTo(source.getPassword());
  }

  @Test
  void shouldReturnNullWhenUserUserAppIsNull() {
    final UserApp source = null;
    assertThat(mapper.map(source)).isNull();
  }
}
