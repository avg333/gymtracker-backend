package org.avillar.gymtracker.workoutsapi.setgroup.deletesetgroup.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.common.sort.application.EntitySorter;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.application.DeleteSetGroupServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteSetGroupServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private DeleteSetGroupServiceImpl deleteSetGroupService;

  @Mock private SetGroupDao setGroupDao;

  @Mock private AuthWorkoutsService authWorkoutsService;
  @Mock private EntitySorter entitySorter;

  @Test
  void deleteOk() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    final SetGroup setGroupFirst = new SetGroup(null, UUID.randomUUID(), workout, new HashSet<>());
    setGroupFirst.setListOrder(0);
    setGroupFirst.setId(UUID.randomUUID());
    final SetGroup setGroupSecond = new SetGroup(null, UUID.randomUUID(), workout, new HashSet<>());
    setGroupSecond.setListOrder(1);
    setGroupSecond.setId(UUID.randomUUID());
    workout.setSetGroups(Set.of(setGroupFirst, setGroupSecond));

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupFirst.getId()))
        .thenReturn(List.of(setGroupFirst));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(setGroupFirst, AuthOperations.DELETE);
    when(setGroupDao.getSetGroupsByWorkoutId(workout.getId())).thenReturn(workout.getSetGroups());

    Assertions.assertDoesNotThrow(() -> deleteSetGroupService.execute(setGroupFirst.getId()));
    verify(setGroupDao).deleteById(setGroupFirst.getId());
    verify(entitySorter).sortDelete(workout.getSetGroups(), setGroupFirst);
    verify(setGroupDao).saveAll(workout.getSetGroups());
  }

  @Test
  void deleteOkNoReorder() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    final SetGroup setGroupFirst = new SetGroup(null, UUID.randomUUID(), workout, new HashSet<>());
    setGroupFirst.setListOrder(0);
    setGroupFirst.setId(UUID.randomUUID());
    final SetGroup setGroupSecond = new SetGroup(null, UUID.randomUUID(), workout, new HashSet<>());
    setGroupSecond.setListOrder(1);
    setGroupSecond.setId(UUID.randomUUID());
    workout.setSetGroups(Set.of(setGroupFirst, setGroupSecond));

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupSecond.getId()))
        .thenReturn(List.of(setGroupSecond));
    Mockito.doNothing()
        .when(authWorkoutsService)
        .checkAccess(setGroupSecond, AuthOperations.DELETE);
    when(setGroupDao.getSetGroupsByWorkoutId(workout.getId())).thenReturn(workout.getSetGroups());

    Assertions.assertDoesNotThrow(() -> deleteSetGroupService.execute(setGroupSecond.getId()));
    verify(setGroupDao).deleteById(setGroupSecond.getId());
    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setGroupDao, never()).saveAll(any());
  }

  @Test
  void deleteNotFound() {
    final UUID setGroupId = UUID.randomUUID();

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class, () -> deleteSetGroupService.execute(setGroupId));
    assertEquals(SetGroup.class.getSimpleName(), exception.getClassName());
    assertEquals(setGroupId, exception.getId());
    verify(setGroupDao, never()).deleteById(any());
    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setGroupDao, never()).saveAll(any());
  }

  @Test
  void deleteNotPermission() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final UUID userId = UUID.randomUUID();
    final AuthOperations deleteOperation = AuthOperations.DELETE;

    when(setGroupDao.getSetGroupWithWorkoutById(setGroup.getId())).thenReturn(List.of(setGroup));
    doThrow(new IllegalAccessException(setGroup, deleteOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(setGroup, deleteOperation);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class, () -> deleteSetGroupService.execute(setGroup.getId()));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(setGroup.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(deleteOperation, exception.getAuthOperations());
    verify(setGroupDao, never()).deleteById(any());
    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setGroupDao, never()).saveAll(any());
  }
}
