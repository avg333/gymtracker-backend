package org.avillar.gymtracker.workoutapi.common.facade.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.util.List;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.model.SetEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.model.SetGroupEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutEntity;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class WorkoutFacadeMapperImplTest {

  private final WorkoutFacadeMapper mapper = Mappers.getMapper(WorkoutFacadeMapper.class);

  @Test
  void shouldMapSetGroupListToSetGroupEntityListSuccessfully() {
    final List<SetGroup> setGroups = Instancio.createList(SetGroup.class);
    assertThat(mapper.mapSetGroups(setGroups)).hasSameSizeAs(setGroups);
  }

  @Test
  void shouldReturnNullWhenSetGroupListEntityIsNull() {
    assertThat(mapper.mapSetGroups(null)).isNull();
  }

  @Test
  void shouldMapSetListToSetEntityList() {
    final List<Set> setList = Instancio.createList(Set.class);
    assertThat(mapper.mapSets(setList)).isNotNull().hasSameSizeAs(setList);
  }

  @Test
  void shouldReturnNullWhenSetListIsNull() {
    assertThat(mapper.mapSets(null)).isNull();
  }

  @Test
  void shouldReturnNullWhenWorkoutIsNull() {
    final Workout workout = null;
    assertThat(mapper.map(workout)).isNull();
  }

  @Test
  void shouldReturnNullWhenSetGroupIsNull() {
    final SetGroup setGroup = null;
    assertThat(mapper.map(setGroup)).isNull();
  }

  @Test
  void shouldReturnNullWhenSetIsNull() {
    final Set set = null;
    assertThat(mapper.map(set)).isNull();
  }

  @Test
  void shouldMapWorkoutToWorkoutEntity() {
    final Workout workout = Instancio.create(Workout.class);

    final WorkoutEntity workoutEntity = mapper.map(workout);
    assertThat(workoutEntity).isNotNull();
    assertThat(workoutEntity.getId()).isEqualTo(workout.getId());
    assertThat(workoutEntity.getDescription()).isEqualTo(workout.getDescription());
    assertThat(workoutEntity.getUserId()).isEqualTo(workout.getUserId());
    assertThat(workoutEntity.getSetGroups()).isNotNull().isEmpty();

    if (workout.getDate() != null) {
      assertThat(workoutEntity.getDate()).isEqualTo(Date.valueOf(workout.getDate()));
    } else {
      assertThat(workoutEntity.getDate()).isNull();
    }
  }

  @Test
  void shouldMapSetGroupToSetGroupEntitySuccessfully() {
    final SetGroup setGroup = Instancio.create(SetGroup.class);

    final SetGroupEntity setGroupEntity = mapper.map(setGroup);
    assertThat(setGroupEntity).isNotNull();
    assertThat(setGroupEntity.getId()).isEqualTo(setGroup.getId());
    assertThat(setGroupEntity.getListOrder()).isEqualTo(setGroup.getListOrder());
    assertThat(setGroupEntity.getDescription()).isEqualTo(setGroup.getDescription());
    assertThat(setGroupEntity.getExerciseId()).isEqualTo(setGroup.getExerciseId());
    assertThat(setGroupEntity.getRest()).isEqualTo(setGroup.getRest());
    assertThat(setGroupEntity.getEccentric()).isEqualTo(setGroup.getEccentric());
    assertThat(setGroupEntity.getFirstPause()).isEqualTo(setGroup.getFirstPause());
    assertThat(setGroupEntity.getConcentric()).isEqualTo(setGroup.getConcentric());
    assertThat(setGroupEntity.getSecondPause()).isEqualTo(setGroup.getSecondPause());
    assertThat(setGroupEntity.getSuperSetWithNext()).isEqualTo(setGroup.getSuperSetWithNext());
    assertThat(setGroupEntity.getSets()).isNotNull().isEmpty();

    if (setGroup.getWorkout() != null) {
      assertThat(setGroupEntity.getWorkout()).isNotNull();
      assertThat(setGroupEntity.getWorkout().getId()).isEqualTo(setGroup.getWorkout().getId());
    } else {
      assertThat(setGroupEntity.getWorkout()).isNull();
    }
  }

  @Test
  void shouldMapSetToSetEntity() {
    final Set set = Instancio.create(Set.class);

    final SetEntity setEntity = mapper.map(set);
    assertThat(setEntity).isNotNull();
    assertThat(setEntity.getId()).isEqualTo(set.getId());
    assertThat(setEntity.getListOrder()).isEqualTo(set.getListOrder());
    assertThat(setEntity.getDescription()).isEqualTo(set.getDescription());
    assertThat(setEntity.getReps()).isEqualTo(set.getReps());
    assertThat(setEntity.getRir()).isEqualTo(set.getRir());
    assertThat(setEntity.getWeight()).isEqualTo(set.getWeight());

    if (set.getCompletedAt() != null) {
      assertThat(setEntity.getCompletedAt()).isNotNull();
      assertThat(setEntity.getCompletedAt().getTime()).isEqualTo(set.getCompletedAt().getTime());
    } else {
      assertThat(setEntity.getCompletedAt()).isNull();
    }

    if (set.getSetGroup() != null) {
      assertThat(setEntity.getSetGroup()).isNotNull();
      assertThat(setEntity.getSetGroup().getId()).isEqualTo(set.getSetGroup().getId());
    } else {
      assertThat(setEntity.getSetGroup()).isNull();
    }
  }
}
