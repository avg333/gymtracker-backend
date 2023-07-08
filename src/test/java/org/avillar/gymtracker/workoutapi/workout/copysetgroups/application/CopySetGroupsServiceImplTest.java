package org.avillar.gymtracker.workoutapi.workout.copysetgroups.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.NotImplementedException;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.mapper.CopySetGroupsServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.model.CopySetGroupsResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CopySetGroupsServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CopySetGroupsServiceImpl copySetGroupsService;

  @Mock private WorkoutDao workoutDao;
  @Mock private SetGroupDao setGroupDao;
  @Mock private SetDao setDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private CopySetGroupsServiceMapperImpl copySetGroupsServiceMapper;

  @Test
  void copyOk() {
    final Workout workoutDestination = getWorkout();
    final Workout workoutSource = getWorkout();

    when(workoutDao.getFullWorkoutByIds(List.of(workoutDestination.getId(), workoutSource.getId())))
        .thenReturn(List.of(workoutDestination, workoutSource));
    doNothing().when(authWorkoutsService).checkAccess(workoutDestination, AuthOperations.UPDATE);
    doNothing().when(authWorkoutsService).checkAccess(workoutSource, AuthOperations.READ);

    final List<CopySetGroupsResponseApplication> result =
        copySetGroupsService.execute(workoutDestination.getId(), workoutSource.getId(), true);

    // assertThat(result).hasSameSizeAs(expected); TODO Acabar tests
    // assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    verify(setGroupDao).saveAll(any());
    verify(setDao).saveAll(any());
  }

  private Workout getWorkout() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    final List<SetGroup> setGroups = easyRandom.objects(SetGroup.class, 5).toList();
    for (int i = 0; i < setGroups.size(); i++) {
      final SetGroup setGroup = setGroups.get(i);
      setGroup.setListOrder(i);
      setGroup.setWorkout(workout);
      final List<Set> sets = easyRandom.objects(Set.class, 5).toList();
      for (int j = 0; j < sets.size(); j++) {
        final Set set = sets.get(j);
        set.setListOrder(j);
        set.setSetGroup(setGroup);
      }
      setGroup.setSets(new HashSet<>(sets));
    }
    workout.setSetGroups(new HashSet<>(setGroups));
    return workout;
  }

  @Test
  void notImplemented() {
    assertThrows(
        NotImplementedException.class,
        () -> copySetGroupsService.execute(UUID.randomUUID(), UUID.randomUUID(), false));
    verify(setGroupDao, never()).saveAll(any());
    verify(setDao, never()).saveAll(any());
  }

  @Test
  void sourceNotFound() {
    final Workout workoutDestination = easyRandom.nextObject(Workout.class);
    final UUID workoutSourceId = UUID.randomUUID();

    when(workoutDao.getFullWorkoutByIds(List.of(workoutDestination.getId(), workoutSourceId)))
        .thenReturn(List.of(workoutDestination));

    final EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class,
            () -> copySetGroupsService.execute(workoutDestination.getId(), workoutSourceId, true));
    assertEquals(Workout.class.getSimpleName(), exception.getClassName());
    assertEquals(workoutSourceId, exception.getId());
    verify(setGroupDao, never()).saveAll(any());
    verify(setDao, never()).saveAll(any());
  }

  @Test
  void destinationNotFound() {
    final Workout workoutSource = easyRandom.nextObject(Workout.class);
    final UUID workoutDestinationId = UUID.randomUUID();

    when(workoutDao.getFullWorkoutByIds(List.of(workoutDestinationId, workoutSource.getId())))
        .thenReturn(List.of(workoutSource));

    final EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class,
            () -> copySetGroupsService.execute(workoutDestinationId, workoutSource.getId(), true));
    assertEquals(Workout.class.getSimpleName(), exception.getClassName());
    assertEquals(workoutDestinationId, exception.getId());
    verify(setGroupDao, never()).saveAll(any());
    verify(setDao, never()).saveAll(any());
  }

  @Test
  void destinationNotPermission() {
    final Workout workoutDestination = easyRandom.nextObject(Workout.class);
    final Workout workoutSource = easyRandom.nextObject(Workout.class);
    final UUID userId = UUID.randomUUID();
    final AuthOperations updateOperation = AuthOperations.UPDATE;

    when(workoutDao.getFullWorkoutByIds(List.of(workoutDestination.getId(), workoutSource.getId())))
        .thenReturn(List.of(workoutDestination, workoutSource));
    doThrow(new IllegalAccessException(workoutDestination, updateOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(workoutDestination, updateOperation);

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class,
            () ->
                copySetGroupsService.execute(
                    workoutDestination.getId(), workoutSource.getId(), true));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workoutDestination.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(updateOperation, exception.getAuthOperations());
    verify(setGroupDao, never()).saveAll(any());
    verify(setDao, never()).saveAll(any());
  }

  @Test
  void sourceNotPermission() {
    final Workout workoutDestination = easyRandom.nextObject(Workout.class);
    final Workout workoutSource = easyRandom.nextObject(Workout.class);
    final UUID userId = UUID.randomUUID();
    final AuthOperations readOperation = AuthOperations.READ;

    when(workoutDao.getFullWorkoutByIds(List.of(workoutDestination.getId(), workoutSource.getId())))
        .thenReturn(List.of(workoutDestination, workoutSource));
    doNothing().when(authWorkoutsService).checkAccess(workoutDestination, AuthOperations.UPDATE);
    doThrow(new IllegalAccessException(workoutSource, readOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(workoutSource, readOperation);

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class,
            () ->
                copySetGroupsService.execute(
                    workoutDestination.getId(), workoutSource.getId(), true));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workoutSource.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(readOperation, exception.getAuthOperations());
    verify(setGroupDao, never()).saveAll(any());
    verify(setDao, never()).saveAll(any());
  }
}
