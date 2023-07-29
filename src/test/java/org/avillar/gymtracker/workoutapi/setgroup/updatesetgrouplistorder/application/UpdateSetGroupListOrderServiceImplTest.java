package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class UpdateSetGroupListOrderServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UpdateSetGroupListOrderServiceImpl updateSetGroupListOrderService;

  @Mock private SetGroupDao setGroupDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private EntitySorter entitySorter;
  @Spy private UpdateSetGroupListOrderServiceMapperImpl updateSetGroupListOrderServiceMapper;

  @Test
  void updateOk() {
    final List<SetGroup> setGroups = getSetGroups();
    final SetGroup setGroupFirst = setGroups.get(0);
    final int newPosition = 3;

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupFirst.getId()))
        .thenReturn(List.of(setGroupFirst));
    when(setGroupDao.getSetGroupsByWorkoutId(setGroupFirst.getWorkout().getId()))
        .thenReturn(new ArrayList<>(setGroups));
    doNothing().when(authWorkoutsService).checkAccess(setGroupFirst, AuthOperations.UPDATE);

    final List<UpdateSetGroupListOrderResponseApplication> result =
        updateSetGroupListOrderService.execute(setGroupFirst.getId(), newPosition);
    //    assertThat(result).hasSameSizeAs(sets);// FIXME
    //    assertThat(result)
    //        .usingRecursiveComparison()
    //        .ignoringFields("listOrder")
    //        .isEqualTo(sets); // FIXME
    // verify(entitySorter).sortUpdate(setGroups, setGroupFirst, newPosition);
    //  verify(setGroupDao).saveAll(setGroups);
  }

  @Test
  void updateSameValue() {
    final List<SetGroup> setGroups = getSetGroups();
    final SetGroup setGroupLast = setGroups.get(setGroups.size() - 1);

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupLast.getId()))
        .thenReturn(List.of(setGroupLast));
    when(setGroupDao.getSetGroupsByWorkoutId(setGroupLast.getWorkout().getId()))
        .thenReturn(setGroups);
    doNothing().when(authWorkoutsService).checkAccess(setGroupLast, AuthOperations.UPDATE);

    final List<UpdateSetGroupListOrderResponseApplication> result =
        updateSetGroupListOrderService.execute(setGroupLast.getId(), setGroupLast.getListOrder());
    assertThat(result).hasSameSizeAs(setGroups);
    assertThat(result).usingRecursiveComparison().isEqualTo(setGroups);
    verify(entitySorter, never()).sortUpdate(any(), any(), anyInt());
    verify(setGroupDao, never()).saveAll(any());
  }

  private List<SetGroup> getSetGroups() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    final List<SetGroup> setGroups = easyRandom.objects(SetGroup.class, 5).toList();
    for (int i = 0; i < setGroups.size(); i++) {
      final SetGroup setGroup = setGroups.get(i);
      setGroup.setListOrder(i);
      setGroup.setWorkout(workout);
      setGroup.setSets(
          easyRandom
              .objects(org.avillar.gymtracker.workoutapi.domain.Set.class, 5)
              .collect(Collectors.toSet()));
      setGroup.getSets().forEach(set -> set.setSetGroup(setGroup));
    }
    return setGroups;
  }

  @Test
  void setGroupNotFound() {
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
    final AuthOperations updateOperation = AuthOperations.UPDATE;

    when(setGroupDao.getSetGroupWithWorkoutById(setGroup.getId())).thenReturn(List.of(setGroup));
    doThrow(new IllegalAccessException(setGroup, updateOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(setGroup, updateOperation);

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class,
            () -> updateSetGroupListOrderService.execute(setGroup.getId(), listOrder));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(setGroup.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(updateOperation, exception.getAuthOperations());
    verify(entitySorter, never()).sortUpdate(any(), any(), anyInt());
    verify(setGroupDao, never()).saveAll(any());
  }
}
