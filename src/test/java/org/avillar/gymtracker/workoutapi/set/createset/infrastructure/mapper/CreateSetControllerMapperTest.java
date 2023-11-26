package org.avillar.gymtracker.workoutapi.set.createset.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetRequest;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class CreateSetControllerMapperTest {

  private final CreateSetControllerMapper mapper =
      Mappers.getMapper(CreateSetControllerMapper.class);

  @Test
  void shouldMapCreateSetRequestToSet() {
    final CreateSetRequest createSetRequest = Instancio.create(CreateSetRequest.class);

    final Date now = new Date();
    final Set set = mapper.map(createSetRequest);
    assertThat(set).isNotNull();
    assertThat(set.getDescription()).isEqualTo(createSetRequest.description());
    assertThat(set.getReps()).isEqualTo(createSetRequest.reps());
    assertThat(set.getRir()).isEqualTo(createSetRequest.rir());
    assertThat(set.getWeight()).isEqualTo(createSetRequest.weight());
    if (createSetRequest.completed()) {
      assertThat(set.getCompletedAt()).isNotNull().isCloseTo(now, TimeUnit.SECONDS.toMillis(5));
    } else {
      assertThat(set.getCompletedAt()).isNull();
    }
  }

  @Test
  void shouldReturnNullWhenCreateSetRequestIsNull() {
    final CreateSetRequest createSetRequest = null;
    assertThat(mapper.map(createSetRequest)).isNull();
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldMapSetToCreateSetResponse(final boolean hasSetGroup) {
    final Set set = Instancio.create(Set.class);
    if (!hasSetGroup) {
      set.setSetGroup(null);
    }

    final CreateSetResponse createSetResponse = mapper.map(set);
    assertThat(createSetResponse).isNotNull();
    assertThat(set.getId()).isEqualTo(createSetResponse.id());
    assertThat(set.getListOrder()).isEqualTo(createSetResponse.listOrder());
    assertThat(set.getDescription()).isEqualTo(createSetResponse.description());
    assertThat(set.getReps()).isEqualTo(createSetResponse.reps());
    assertThat(set.getRir()).isEqualTo(createSetResponse.rir());
    assertThat(set.getWeight()).isEqualTo(createSetResponse.weight());
    assertThat(set.getCompletedAt()).isEqualTo(createSetResponse.completedAt());
    if (set.getSetGroup() != null) {
      assertThat(set.getSetGroup().getId()).isEqualTo(createSetResponse.setGroup().id());
    } else {
      assertThat(createSetResponse.setGroup()).isNull();
    }
  }

  @Test
  void shouldReturnNullWhenSetIsNull() {
    final Set set = null;
    assertThat(mapper.map(set)).isNull();
  }
}
