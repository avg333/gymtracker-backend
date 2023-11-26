package org.avillar.gymtracker.workoutapi.common.facade.setgroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.SetGroupDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.model.SetGroupEntity;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.mapper.WorkoutEntityFacadeMapper;
import org.avillar.gymtracker.workoutapi.common.facade.mapper.WorkoutFacadeMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class SetGroupFacadeImplTest {

  @InjectMocks private SetGroupFacadeImpl facade;

  @Mock private SetGroupDao dao;
  @Mock private WorkoutFacadeMapper workoutFacadeMapper;
  @Mock private WorkoutEntityFacadeMapper workoutEntityFacadeMapper;

  @Test
  void shouldGetSetGroupFullSuccessfully() throws SetGroupNotFoundException {
    final UUID setGroupId = UUID.randomUUID();
    final SetGroupEntity setGroupEntity = Instancio.create(SetGroupEntity.class);
    final SetGroup expected = Instancio.create(SetGroup.class);

    when(dao.getSetGroupFullByIds(Set.of(setGroupId))).thenReturn(List.of(setGroupEntity));
    when(workoutEntityFacadeMapper.map(setGroupEntity)).thenReturn(expected);

    assertThat(facade.getSetGroupFull(setGroupId)).isEqualTo(expected);
  }

  @Test
  void shouldThrowEntityNotFoundExceptionWhenSetGroupFullIsNotFound() {
    final UUID setGroupId = UUID.randomUUID();
    final SetGroupNotFoundException exception = new SetGroupNotFoundException(setGroupId);

    when(dao.getSetGroupFullByIds(Set.of(setGroupId))).thenReturn(Collections.emptyList());

    assertThatThrownBy(() -> facade.getSetGroupFull(setGroupId)).isEqualTo(exception);
  }

  @Test
  void shouldGetSetGroupFullByIdsSuccessfully() {
    final List<UUID> setGroupIds = Instancio.createList(UUID.class);
    final List<SetGroupEntity> setGroupEntities = Instancio.createList(SetGroupEntity.class);
    final List<SetGroup> expected = Instancio.createList(SetGroup.class);

    final ArgumentCaptor<Set<UUID>> captor = ArgumentCaptor.forClass(Set.class);

    when(dao.getSetGroupFullByIds(captor.capture())).thenReturn(setGroupEntities);
    when(workoutEntityFacadeMapper.mapSetGroups(setGroupEntities)).thenReturn(expected);

    assertThat(facade.getSetGroupFullByIds(setGroupIds)).isEqualTo(expected);

    assertThat(captor.getValue()).containsExactlyInAnyOrderElementsOf(setGroupIds);
  }

  @Test
  void shouldGetSetGroupFullByIdsSuccessfullyWithRepeatedUUIDs() {
    final List<UUID> setGroupIds = Instancio.createList(UUID.class);
    setGroupIds.add(setGroupIds.get(0));
    final List<SetGroupEntity> setGroupEntities = Instancio.createList(SetGroupEntity.class);
    final List<SetGroup> expected = Instancio.createList(SetGroup.class);

    final ArgumentCaptor<Set<UUID>> captor = ArgumentCaptor.forClass(Set.class);

    when(dao.getSetGroupFullByIds(captor.capture())).thenReturn(setGroupEntities);
    when(workoutEntityFacadeMapper.mapSetGroups(setGroupEntities)).thenReturn(expected);

    assertThat(facade.getSetGroupFullByIds(setGroupIds)).isEqualTo(expected);

    assertThat(captor.getValue())
        .containsExactlyInAnyOrderElementsOf(setGroupIds.stream().distinct().toList());
  }

  @Test
  void shouldGetSetGroupWithWorkoutSuccessfully() throws SetGroupNotFoundException {
    final UUID setGroupId = UUID.randomUUID();
    final SetGroupEntity setGroupEntity = Instancio.create(SetGroupEntity.class);
    final SetGroup expected = Instancio.create(SetGroup.class);

    when(dao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(Optional.of(setGroupEntity));
    when(workoutEntityFacadeMapper.map(setGroupEntity)).thenReturn(expected);

    assertThat(facade.getSetGroupWithWorkout(setGroupId)).isEqualTo(expected);
  }

  @Test
  void shouldThrowEntityNotFoundExceptionWhenSetGroupWithWorkoutIsNotFound() {
    final UUID setGroupId = UUID.randomUUID();
    final SetGroupNotFoundException exception = new SetGroupNotFoundException(setGroupId);

    when(dao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> facade.getSetGroupWithWorkout(setGroupId)).isEqualTo(exception);
  }

  @Test
  void shouldGetSetGroupFullByWorkoutIdSuccessfully() {
    final UUID workoutId = UUID.randomUUID();
    final List<SetGroupEntity> setGroupEntities = Instancio.createList(SetGroupEntity.class);
    final List<SetGroup> expected = Instancio.createList(SetGroup.class);

    when(dao.getSetGroupsByWorkoutId(workoutId)).thenReturn(setGroupEntities);
    when(workoutEntityFacadeMapper.mapSetGroups(setGroupEntities)).thenReturn(expected);

    assertThat(facade.getSetGroupsByWorkoutId(workoutId)).isEqualTo(expected);
  }

  @Test
  void shouldGetSetGroupFullByUserIdAndExerciseIdSuccessfully() {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final List<SetGroupEntity> setGroupEntities = Instancio.createList(SetGroupEntity.class);
    final List<SetGroup> expected = Instancio.createList(SetGroup.class);

    when(dao.getSetGroupsFullByUserIdAndExerciseId(userId, exerciseId))
        .thenReturn(setGroupEntities);
    when(workoutEntityFacadeMapper.mapSetGroups(setGroupEntities)).thenReturn(expected);

    assertThat(facade.getSetGroupsFullByUserIdAndExerciseId(userId, exerciseId))
        .isEqualTo(expected);
  }

  @Test
  void shouldSaveSetSuccessfully() {
    final SetGroup setGroupBeforeSave = Instancio.create(SetGroup.class);
    final SetGroupEntity setGroupEntityBeforeSave = Instancio.create(SetGroupEntity.class);
    final SetGroupEntity setGroupEntityAfterSave = Instancio.create(SetGroupEntity.class);
    final SetGroup setGroupAfterSave = Instancio.create(SetGroup.class);

    when(workoutFacadeMapper.map(setGroupBeforeSave)).thenReturn(setGroupEntityBeforeSave);
    when(dao.save(setGroupEntityBeforeSave)).thenReturn(setGroupEntityAfterSave);
    when(workoutEntityFacadeMapper.map(setGroupEntityAfterSave)).thenReturn(setGroupAfterSave);

    assertThat(facade.saveSetGroup(setGroupBeforeSave)).isEqualTo(setGroupAfterSave);
  }

  @Test
  void shouldSaveSetGroupsSuccessfully() {
    final List<SetGroup> setGroupsBeforeSave = Instancio.createList(SetGroup.class);
    final List<SetGroupEntity> setEntitiesBeforeSave = Instancio.createList(SetGroupEntity.class);
    final List<SetGroupEntity> setEntitiesAfterSave = Instancio.createList(SetGroupEntity.class);
    final List<SetGroup> setGroupsAfterSave = Instancio.createList(SetGroup.class);

    when(workoutFacadeMapper.mapSetGroups(setGroupsBeforeSave)).thenReturn(setEntitiesBeforeSave);
    when(dao.saveAll(setEntitiesBeforeSave)).thenReturn(setEntitiesAfterSave);
    when(workoutEntityFacadeMapper.mapSetGroups(setEntitiesAfterSave))
        .thenReturn(setGroupsAfterSave);

    assertThat(facade.saveSetGroups(setGroupsBeforeSave)).isEqualTo(setGroupsAfterSave);
  }

  @Test
  void shouldDeleteSetGroupSuccessfully() {
    final UUID setGroupId = UUID.randomUUID();

    doNothing().when(dao).deleteById(setGroupId);

    assertDoesNotThrow(() -> facade.deleteSetGroup(setGroupId));
  }
}
