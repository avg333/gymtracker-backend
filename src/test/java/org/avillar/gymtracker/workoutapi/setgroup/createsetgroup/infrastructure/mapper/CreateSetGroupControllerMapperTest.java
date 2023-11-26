package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupRequest;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class CreateSetGroupControllerMapperTest {

  private final CreateSetGroupControllerMapper mapper =
      Mappers.getMapper(CreateSetGroupControllerMapper.class);

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldMapSetGroupToCreateSetGroupResponse(final boolean hasWorkout) {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    if (!hasWorkout) {
      setGroup.setWorkout(null);
    }

    final CreateSetGroupResponse createSetGroupResponse = mapper.map(setGroup);
    assertThat(createSetGroupResponse).isNotNull();
    assertThat(createSetGroupResponse.id()).isEqualTo(setGroup.getId());
    assertThat(createSetGroupResponse.listOrder()).isEqualTo(setGroup.getListOrder());
    assertThat(createSetGroupResponse.description()).isEqualTo(setGroup.getDescription());
    assertThat(createSetGroupResponse.exerciseId()).isEqualTo(setGroup.getExerciseId());

    if (setGroup.getWorkout() != null) {
      assertThat(createSetGroupResponse.workout().id()).isEqualTo(setGroup.getWorkout().getId());
    } else {
      assertThat(createSetGroupResponse.workout()).isNull();
    }
  }

  @Test
  void shouldReturnNullWhenSetGroupIsNull() {
    final SetGroup setGroup = null;
    assertThat(mapper.map(setGroup)).isNull();
  }

  @Test
  void shouldMapCreateSetGroupRequestToSetGroup() {
    final CreateSetGroupRequest createSetGroupRequest =
        Instancio.create(CreateSetGroupRequest.class);

    final SetGroup setGroup = mapper.map(createSetGroupRequest);
    assertThat(setGroup).isNotNull();
    assertThat(setGroup.getDescription()).isEqualTo(createSetGroupRequest.description());
    assertThat(setGroup.getExerciseId()).isEqualTo(createSetGroupRequest.exerciseId());
  }

  @Test
  void shouldReturnNullWhenCreateSetGroupRequestIsNull() {
    final CreateSetGroupRequest createSetGroupRequest = null;
    assertThat(mapper.map(createSetGroupRequest)).isNull();
  }
}
