package org.avillar.gymtracker.workoutapi.common.facade.set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.SetDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.model.SetEntity;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.mapper.WorkoutEntityFacadeMapper;
import org.avillar.gymtracker.workoutapi.common.facade.mapper.WorkoutFacadeMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class SetFacadeImplTest {

  @InjectMocks private SetFacadeImpl facade;

  @Mock private SetDao dao;
  @Mock private WorkoutFacadeMapper workoutFacadeMapper;
  @Mock private WorkoutEntityFacadeMapper workoutEntityFacadeMapper;

  @Test
  void shouldGetFullSuccessfully() throws SetNotFoundException {
    final UUID setId = UUID.randomUUID();
    final SetEntity setEntity = Instancio.create(SetEntity.class);
    final Set expected = Instancio.create(Set.class);

    when(dao.getSetFullById(setId)).thenReturn(Optional.of(setEntity));
    when(workoutEntityFacadeMapper.map(setEntity)).thenReturn(expected);

    assertThat(facade.getSetFull(setId)).isEqualTo(expected);
  }

  @Test
  void shouldThrowEntityNotFoundExceptionWhenSetFullIsNotFound() {
    final UUID setId = UUID.randomUUID();
    final SetNotFoundException exception = new SetNotFoundException(setId);

    when(dao.getSetFullById(setId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> facade.getSetFull(setId)).isEqualTo(exception);
  }

  @Test
  void shouldGetSetsBySetGroupIdSuccessfully() {
    final UUID setGroupId = UUID.randomUUID();
    final List<SetEntity> setEntities = Instancio.createList(SetEntity.class);
    final List<Set> expected = Instancio.createList(Set.class);

    when(dao.getSetsBySetGroupId(setGroupId)).thenReturn(setEntities);
    when(workoutEntityFacadeMapper.mapSets(setEntities)).thenReturn(expected);

    assertThat(facade.getSetsBySetGroupId(setGroupId)).isEqualTo(expected);
  }

  @Test
  void shouldGetSetGroupExerciseHistorySuccessfully() {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final List<SetEntity> setEntities = Instancio.createList(SetEntity.class);
    final Set expected = Instancio.create(Set.class);

    when(dao.findLastSetForExerciseAndUserAux(
            setGroup.getWorkout().getUserId(),
            setGroup.getExerciseId(),
            Date.valueOf(setGroup.getWorkout().getDate())))
        .thenReturn(setEntities);
    when(workoutEntityFacadeMapper.map(setEntities.get(0))).thenReturn(expected);

    assertThat(facade.getSetGroupExerciseHistory(setGroup)).isEqualTo(expected);
  }

  @Test
  void shouldThrowEntityNotFoundExceptionWhenGetSetGroupExerciseHistoryIsNotFound() {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final List<SetEntity> setEntities = Collections.emptyList();

    when(dao.findLastSetForExerciseAndUserAux(
            setGroup.getWorkout().getUserId(),
            setGroup.getExerciseId(),
            Date.valueOf(setGroup.getWorkout().getDate())))
        .thenReturn(setEntities);

    assertThat(facade.getSetGroupExerciseHistory(setGroup)).isNull();
  }

  @Test
  void shouldSaveSetSuccessfully() {
    final Set set = Instancio.create(Set.class);
    final SetEntity setEntityBeforeSave = Instancio.create(SetEntity.class);
    final SetEntity setEntityAfterSave = Instancio.create(SetEntity.class);
    final Set expected = Instancio.create(Set.class);

    when(workoutFacadeMapper.map(set)).thenReturn(setEntityBeforeSave);
    when(dao.save(setEntityBeforeSave)).thenReturn(setEntityAfterSave);
    when(workoutEntityFacadeMapper.map(setEntityAfterSave)).thenReturn(expected);

    assertThat(facade.saveSet(set)).isEqualTo(expected);
  }

  @Test
  void shouldSaveSetsSuccessfully() {
    final List<Set> sets = Instancio.createList(Set.class);
    final List<SetEntity> setEntitiesBeforeSave = Instancio.createList(SetEntity.class);
    final List<SetEntity> setEntitiesAfterSave = Instancio.createList(SetEntity.class);
    final List<Set> expected = Instancio.createList(Set.class);

    when(workoutFacadeMapper.mapSets(sets)).thenReturn(setEntitiesBeforeSave);
    when(dao.saveAll(setEntitiesBeforeSave)).thenReturn(setEntitiesAfterSave);
    when(workoutEntityFacadeMapper.mapSets(setEntitiesAfterSave)).thenReturn(expected);

    assertThat(facade.saveSets(sets)).isEqualTo(expected);
  }

  @Test
  void shouldDeleteSetSuccessfully() {
    final UUID setId = UUID.randomUUID();

    doNothing().when(dao).deleteById(setId);

    assertDoesNotThrow(() -> facade.deleteSet(setId));
  }
}
