package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.model.GetWorkoutSetGroupsResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class GetWorkoutSetGroupsControllerMapperTest {
  private final GetWorkoutSetGroupsControllerMapper mapper =
      Mappers.getMapper(GetWorkoutSetGroupsControllerMapper.class);

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldMapGetWorkoutResponseToGetWorkoutResponseDtoSuccessfully(final boolean hasSetGroups) {
    final Workout source = Instancio.create(Workout.class);
    source.getSetGroups().add(null);
    if (!hasSetGroups) {
      source.setSetGroups(null);
    }

    final GetWorkoutSetGroupsResponse target = mapper.map(source);
    assertThat(target).isNotNull();
    assertThat(target.id()).isEqualTo(source.getId());
    assertThat(target.date()).isEqualTo(source.getDate());
    assertThat(target.description()).isEqualTo(source.getDescription());
    assertThat(target.userId()).isEqualTo(source.getUserId());
    if (source.getSetGroups() != null) {
      assertThat(target.setGroups()).hasSize(source.getSetGroups().size());
      for (int i = 0; i < source.getSetGroups().size(); i++) {
        checkSetGroup(target.setGroups().get(i), source.getSetGroups().get(i));
      }
    } else {
      assertThat(target.setGroups()).isNull();
    }
  }

  void checkSetGroup(final GetWorkoutSetGroupsResponse.SetGroup target, final SetGroup source) {
    if (source == null) {
      assertThat(target).isNull();
      return;
    }

    assertThat(target).isNotNull();
    assertThat(target.id()).isEqualTo(source.getId());
    assertThat(target.listOrder()).isEqualTo(source.getListOrder());
    assertThat(target.description()).isEqualTo(source.getDescription());
    assertThat(target.exerciseId()).isEqualTo(source.getExerciseId());
  }

  @Test
  void shouldReturnNullWhenGetWorkoutResponseIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
