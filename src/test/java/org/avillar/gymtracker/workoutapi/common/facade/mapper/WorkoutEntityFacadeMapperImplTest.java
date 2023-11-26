package org.avillar.gymtracker.workoutapi.common.facade.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
class WorkoutEntityFacadeMapperImplTest {

  private final WorkoutEntityFacadeMapper mapper =
      Mappers.getMapper(WorkoutEntityFacadeMapper.class);

  @Test
  void shouldMapDateMapToLocalDateMap() {
    final Map<Date, UUID> workoutsIdAndDate = Instancio.createMap(Date.class, UUID.class);
    final Map<LocalDate, UUID> mappedWorkoutsIdAndDate = mapper.mapMap(workoutsIdAndDate);
    assertThat(mappedWorkoutsIdAndDate).isNotNull().hasSize(workoutsIdAndDate.size());
    workoutsIdAndDate.forEach(
        (key, value) ->
            assertThat(mappedWorkoutsIdAndDate).containsEntry(key.toLocalDate(), value));
  }

  @Test
  void shouldReturnEmptyMapWhenMapDateIsNull() {
    assertThat(mapper.mapMap(null)).isNotNull().isEmpty();
  }

  @Test
  void shouldMapWorkoutEntityListToWorkout() {
    final List<WorkoutEntity> workouts = Instancio.createList(WorkoutEntity.class);
    assertThat(mapper.mapWorkouts(workouts)).isNotNull().hasSameSizeAs(workouts);
  }

  @Test
  void shouldReturnEmptyListWhenWorkoutEntityListIsNull() {
    assertThat(mapper.mapWorkouts(null)).isNotNull().isEmpty();
  }

  @Test
  void shouldMapSetGroupEntityListToSetGroupListSuccessfully() {
    final List<SetGroupEntity> setGroupEntities = Instancio.createList(SetGroupEntity.class);
    assertThat(mapper.mapSetGroups(setGroupEntities)).isNotNull().hasSameSizeAs(setGroupEntities);
  }

  @Test
  void shouldReturnEmptyListWhenSetGroupEntityListIsNull() {
    assertThat(mapper.mapSetGroups(null)).isNotNull().isEmpty();
  }

  @Test
  void shouldMapSetEntityListToSetEntityList() {
    final List<SetEntity> setEntityList = Instancio.createList(SetEntity.class);
    assertThat(mapper.mapSets(setEntityList)).isNotNull().hasSameSizeAs(setEntityList);
  }

  @Test
  void shouldReturnEmptyListWhenSetEntityListIsNull() {
    assertThat(mapper.mapSets(null)).isNotNull().isEmpty();
  }

  @Test
  void shouldMapWorkoutEntityToWorkoutSuccessfully() {
    final WorkoutEntity workoutEntity = Instancio.create(WorkoutEntity.class);

    final Workout workout = mapper.map(workoutEntity);
    assertThat(workout).isNotNull();
    assertThat(workout.getId()).isEqualTo(workoutEntity.getId());
    assertThat(workout.getDescription()).isEqualTo(workoutEntity.getDescription());
    assertThat(workout.getUserId()).isEqualTo(workoutEntity.getUserId());

    if (workoutEntity.getDate() != null) {
      assertThat(Date.valueOf(workout.getDate())).isEqualTo(workoutEntity.getDate());
    } else {
      assertThat(workout.getDate()).isNull();
    }

    if (workoutEntity.getSetGroups() != null) {
      assertThat(workout.getSetGroups()).isNotNull().hasSize(workoutEntity.getSetGroups().size());
    } else {
      assertThat(workout.getSetGroups()).isNull();
    }
  }

  @Test
  void shouldMapSetGroupEntityToSetGroupSuccessfully() {
    final SetGroupEntity setGroupEntity = Instancio.create(SetGroupEntity.class);

    final SetGroup setGroup = mapper.map(setGroupEntity);
    assertThat(setGroup).isNotNull();
    assertThat(setGroup.getId()).isEqualTo(setGroupEntity.getId());
    assertThat(setGroup.getListOrder()).isEqualTo(setGroupEntity.getListOrder());
    assertThat(setGroup.getDescription()).isEqualTo(setGroupEntity.getDescription());
    assertThat(setGroup.getExerciseId()).isEqualTo(setGroupEntity.getExerciseId());
    assertThat(setGroup.getRest()).isEqualTo(setGroupEntity.getRest());
    assertThat(setGroup.getEccentric()).isEqualTo(setGroupEntity.getEccentric());
    assertThat(setGroup.getFirstPause()).isEqualTo(setGroupEntity.getFirstPause());
    assertThat(setGroup.getConcentric()).isEqualTo(setGroupEntity.getConcentric());
    assertThat(setGroup.getSecondPause()).isEqualTo(setGroupEntity.getSecondPause());
    assertThat(setGroup.getSuperSetWithNext()).isEqualTo(setGroupEntity.getSuperSetWithNext());

    if (setGroupEntity.getWorkout() != null) {
      assertThat(setGroup.getWorkout()).isNotNull();
    } else {
      assertThat(setGroup.getWorkout()).isNull();
    }

    if (setGroupEntity.getSets() != null) {
      assertThat(setGroup.getSets()).isNotNull().hasSize(setGroupEntity.getSets().size());
    } else {
      assertThat(setGroup.getSets()).isNotNull().isEmpty();
    }
  }

  @Test
  void shouldMapSetEntityToSet() {
    final SetEntity setEntity = Instancio.create(SetEntity.class);

    final Set set = mapper.map(setEntity);
    assertThat(set).isNotNull();
    assertThat(set.getId()).isEqualTo(setEntity.getId());
    assertThat(set.getListOrder()).isEqualTo(setEntity.getListOrder());
    assertThat(set.getDescription()).isEqualTo(setEntity.getDescription());
    assertThat(set.getReps()).isEqualTo(setEntity.getReps());
    assertThat(set.getRir()).isEqualTo(setEntity.getRir());
    assertThat(set.getWeight()).isEqualTo(setEntity.getWeight());

    if (setEntity.getCompletedAt() != null) {
      assertThat(set.getCompletedAt().getTime()).isEqualTo(setEntity.getCompletedAt().getTime());
    } else {
      assertThat(set.getCompletedAt()).isNull();
    }

    if (setEntity.getSetGroup() != null) {
      assertThat(set.getSetGroup()).isNotNull();
    } else {
      assertThat(set.getSetGroup()).isNull();
    }
  }

  @Test
  void shouldReturnNullWhenWorkoutEntityIsNull() {
    final WorkoutEntity workoutEntity = null;
    assertThat(mapper.map(workoutEntity)).isNull();
  }

  @Test
  void shouldReturnNullWhenSetGroupEntityIsNull() {
    final SetGroupEntity setGroupEntity = null;
    assertThat(mapper.map(setGroupEntity)).isNull();
  }

  @Test
  void shouldReturnNullWhenSetEntityIsNull() {
    final SetEntity setEntity = null;
    assertThat(mapper.map(setEntity)).isNull();
  }
}
