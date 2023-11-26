package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.model.GetSetGroupResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class GetSetGroupControllerMapperTest {

  private final GetSetGroupControllerMapper mapper =
      Mappers.getMapper(GetSetGroupControllerMapper.class);

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldMapSetGroupToGetSetGroupResponse(final boolean hasWorkout) {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    if (!hasWorkout) {
      setGroup.setWorkout(null);
    }

    final GetSetGroupResponse getSetGroupResponse = mapper.map(setGroup);
    assertThat(getSetGroupResponse).isNotNull();
    assertThat(getSetGroupResponse.id()).isEqualTo(setGroup.getId());
    assertThat(getSetGroupResponse.listOrder()).isEqualTo(setGroup.getListOrder());
    assertThat(getSetGroupResponse.description()).isEqualTo(setGroup.getDescription());
    assertThat(getSetGroupResponse.exerciseId()).isEqualTo(setGroup.getExerciseId());

    if (setGroup.getWorkout() != null) {
      assertThat(getSetGroupResponse.workout()).isNotNull();
      assertThat(getSetGroupResponse.workout().id()).isEqualTo(setGroup.getWorkout().getId());
    } else {
      assertThat(getSetGroupResponse.workout()).isNull();
    }
  }

  @Test
  void shouldReturnNullWhenSetGroupIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
