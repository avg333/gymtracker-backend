package org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.common.sort.application.EntitySorter;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    final List<SetGroup> setGroups = getSetGroups();
    final SetGroup setGroupSecond = setGroups.get(1);

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupSecond.getId()))
        .thenReturn(List.of(setGroupSecond));
    doNothing().when(authWorkoutsService).checkAccess(setGroupSecond, AuthOperations.DELETE);
    when(setGroupDao.getSetGroupsByWorkoutId(setGroupSecond.getWorkout().getId()))
        .thenReturn(setGroups);

    assertDoesNotThrow(() -> deleteSetGroupService.execute(setGroupSecond.getId()));
    verify(setGroupDao).deleteById(setGroupSecond.getId());
    verify(entitySorter).sortDelete(setGroups, setGroupSecond); // FIXM
    verify(setGroupDao).saveAll(setGroups); // FIXME It must be called without the deleted SG
  }

  @Test
  void deleteOkNoReorder() {
    final List<SetGroup> setGroups = getSetGroups();
    final SetGroup setGroupLast = setGroups.get(setGroups.size() - 1);

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupLast.getId()))
        .thenReturn(List.of(setGroupLast));
    doNothing().when(authWorkoutsService).checkAccess(setGroupLast, AuthOperations.DELETE);
    when(setGroupDao.getSetGroupsByWorkoutId(setGroupLast.getWorkout().getId()))
        .thenReturn(setGroups);

    assertDoesNotThrow(() -> deleteSetGroupService.execute(setGroupLast.getId()));
    verify(setGroupDao).deleteById(setGroupLast.getId());
    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setGroupDao, never()).saveAll(any());
  }

  private List<SetGroup> getSetGroups() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    final List<SetGroup> setGroups = easyRandom.objects(SetGroup.class, 5).toList();
    for (int i = 0; i < setGroups.size(); i++) {
      final SetGroup setGroup = setGroups.get(i);
      setGroup.setListOrder(i);
      setGroup.setWorkout(workout);
      setGroup.setSets(easyRandom.objects(Set.class, 5).collect(Collectors.toSet()));
      setGroup.getSets().forEach(set -> set.setSetGroup(setGroup));
    }
    return setGroups;
  }

  @Test
  void deleteNotFound() {
    final UUID setGroupId = UUID.randomUUID();

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        assertThrows(
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
        assertThrows(
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
