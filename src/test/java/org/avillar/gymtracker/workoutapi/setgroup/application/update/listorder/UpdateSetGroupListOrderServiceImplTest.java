package org.avillar.gymtracker.workoutapi.setgroup.application.update.listorder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.common.sort.application.EntitySorter;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.listorder.mapper.UpdateSetGroupListOrderServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.listorder.model.UpdateSetGroupListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateSetGroupListOrderServiceImplTest {
  private UpdateSetGroupListOrderService updateSetGroupListOrderService;

  @Mock private SetGroupDao setGroupDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Mock private EntitySorter entitySorter;
  @Spy private UpdateSetGroupListOrderServiceMapperImpl updateSetGroupListOrderServiceMapper;

  @BeforeEach
  void beforeEach() {
    updateSetGroupListOrderService =
        new UpdateSetGroupListOrderServiceImpl(
            setGroupDao, authWorkoutsService, entitySorter, updateSetGroupListOrderServiceMapper);
  }

  @Test
  void updateOk() {
    final UUID setGroupId = UUID.randomUUID();
    final UUID workoutId = UUID.randomUUID();
    final int listOrder = 1;

    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    setGroup.setListOrder(2);
    final Workout workout = new Workout();
    workout.setId(workoutId);
    setGroup.setWorkout(workout);

    final SetGroup setGroup1 = new SetGroup();
    setGroup1.setId(UUID.randomUUID());
    final SetGroup setGroup2 = new SetGroup();
    setGroup2.setId(UUID.randomUUID());

    final Set<SetGroup> setGroups = Set.of(setGroup1, setGroup2, setGroup);

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(List.of(setGroup));
    when(setGroupDao.getSetGroupsByWorkoutId(workoutId)).thenReturn(setGroups);
    Mockito.doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.UPDATE);
    Mockito.doNothing().when(entitySorter).sortUpdate(setGroups, setGroup, setGroup.getListOrder());
    Mockito.verify(setGroupDao, Mockito.times(0))
        .saveAll(Mockito.anyCollection()); // FIXME cambiar a 1

    final UpdateSetGroupListOrderResponseApplication updateSetGroupListOrderResponseApplication =
        updateSetGroupListOrderService.execute(setGroupId, listOrder);
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
  }

  @Test
  void updateNotPermission() {
    final UUID userId = UUID.randomUUID();
    final UUID setGroupId = UUID.randomUUID();
    final int listOrder = 1;

    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    setGroup.setExerciseId(UUID.randomUUID());

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(List.of(setGroup));
    doThrow(new IllegalAccessException(setGroup, AuthOperations.UPDATE, userId))
        .when(authWorkoutsService)
        .checkAccess(setGroup, AuthOperations.UPDATE);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> updateSetGroupListOrderService.execute(setGroupId, listOrder));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(setGroupId, exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.UPDATE, exception.getAuthOperations());
  }

  @Test
  void updateSameValue() {
    final UUID setGroupId = UUID.randomUUID();
    final UUID workoutId = UUID.randomUUID();
    final int listOrder = 2;

    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    setGroup.setListOrder(2);
    final Workout workout = new Workout();
    workout.setId(workoutId);
    setGroup.setWorkout(workout);

    final SetGroup setGroup1 = new SetGroup();
    setGroup1.setId(UUID.randomUUID());
    final SetGroup setGroup2 = new SetGroup();
    setGroup2.setId(UUID.randomUUID());

    final Set<SetGroup> setGroups = Set.of(setGroup1, setGroup2, setGroup);

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(List.of(setGroup));
    when(setGroupDao.getSetGroupsByWorkoutId(workoutId)).thenReturn(setGroups);
    Mockito.doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.UPDATE);
    verify(entitySorter, never()).sortUpdate(setGroups, setGroup, setGroup.getListOrder());

    final UpdateSetGroupListOrderResponseApplication updateSetGroupListOrderResponseApplication =
        updateSetGroupListOrderService.execute(setGroupId, listOrder);
  }
}
