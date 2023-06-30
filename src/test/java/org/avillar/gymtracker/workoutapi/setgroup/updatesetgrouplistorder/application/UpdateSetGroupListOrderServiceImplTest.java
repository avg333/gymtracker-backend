package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.mapper.UpdateSetGroupListOrderServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.model.UpdateSetGroupListOrderResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateSetGroupListOrderServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UpdateSetGroupListOrderServiceImpl updateSetGroupListOrderService;

  @Mock private SetGroupDao setGroupDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Mock private EntitySorter entitySorter;
  @Spy private UpdateSetGroupListOrderServiceMapperImpl updateSetGroupListOrderServiceMapper;

  @Test
  void updateOk() {
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
    when(setGroupDao.getSetGroupsByWorkoutId(workout.getId())).thenReturn(workout.getSetGroups());
    Mockito.doNothing().when(authWorkoutsService).checkAccess(setGroupFirst, AuthOperations.UPDATE);
    Mockito.doNothing()
        .when(entitySorter)
        .sortUpdate(workout.getSetGroups(), setGroupFirst, setGroupFirst.getListOrder());

    final List<UpdateSetGroupListOrderResponseApplication> result =
        updateSetGroupListOrderService.execute(setGroupFirst.getId(), 1);
    assertEquals(workout.getSetGroups().size(), result.size());
    // assertEquals(setGroupFirst.getId(), result.get(0).getId());
    // assertEquals(1, result.get(0).getListOrder()); FIXME
    // assertEquals(setGroupFirst.getExerciseId(), result.get(0).getExerciseId());
    // assertEquals(setGroupFirst.getDescription(), result.get(0).getDescription());
    assertEquals(workout.getId(), result.get(0).getWorkout().getId());
    // assertEquals(setGroupSecond.getId(), result.get(1).getId());
    // assertEquals(0, result.get(1).getListOrder()); FIXME
    //  assertEquals(setGroupSecond.getExerciseId(), result.get(1).getExerciseId());
    // assertEquals(setGroupSecond.getDescription(), result.get(1).getDescription());
    assertEquals(workout.getId(), result.get(1).getWorkout().getId());
    verify(entitySorter).sortUpdate(workout.getSetGroups(), setGroupFirst, 0);
    verify(setGroupDao).saveAll(workout.getSetGroups());
  }

  @Test
  void updateSameValue() {
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
    when(setGroupDao.getSetGroupsByWorkoutId(workout.getId())).thenReturn(workout.getSetGroups());
    Mockito.doNothing().when(authWorkoutsService).checkAccess(setGroupFirst, AuthOperations.UPDATE);

    final List<UpdateSetGroupListOrderResponseApplication> result =
        updateSetGroupListOrderService.execute(setGroupFirst.getId(), 0);
    assertEquals(workout.getSetGroups().size(), result.size());
//    assertEquals(setGroupFirst.getId(), result.get(0).getId());
//    assertEquals(setGroupFirst.getListOrder(), result.get(0).getListOrder());
//    assertEquals(setGroupFirst.getExerciseId(), result.get(0).getExerciseId());
//    assertEquals(setGroupFirst.getDescription(), result.get(0).getDescription()); FIXME
//    assertEquals(workout.getId(), result.get(0).getWorkout().getId());
//    assertEquals(setGroupSecond.getId(), result.get(1).getId());
//    assertEquals(setGroupSecond.getListOrder(), result.get(1).getListOrder());
//    assertEquals(setGroupSecond.getExerciseId(), result.get(1).getExerciseId());
//    assertEquals(setGroupSecond.getDescription(), result.get(1).getDescription());
    assertEquals(workout.getId(), result.get(1).getWorkout().getId());
    verify(entitySorter, never()).sortUpdate(any(), any(), anyInt());
    verify(setGroupDao, never()).saveAll(any());
  }

  @Test
  void updateNotFound() {
    final UUID setGroupId = UUID.randomUUID();
    final int listOrder = 1;

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> updateSetGroupListOrderService.execute(setGroupId, listOrder));
    assertEquals(SetGroup.class.getSimpleName(), exception.getClassName());
    assertEquals(setGroupId, exception.getId());
    verify(entitySorter, never()).sortUpdate(any(), any(), anyInt());
    verify(setGroupDao, never()).saveAll(any());
  }

  @Test
  void updateNotPermission() {
    final UUID userId = UUID.randomUUID();
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final int listOrder = 1;
    final AuthOperations authOperation = AuthOperations.UPDATE;

    when(setGroupDao.getSetGroupWithWorkoutById(setGroup.getId())).thenReturn(List.of(setGroup));
    doThrow(new IllegalAccessException(setGroup, authOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(setGroup, authOperation);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> updateSetGroupListOrderService.execute(setGroup.getId(), listOrder));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(setGroup.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperation, exception.getAuthOperations());
    verify(entitySorter, never()).sortUpdate(any(), any(), anyInt());
    verify(setGroupDao, never()).saveAll(any());
  }
}
