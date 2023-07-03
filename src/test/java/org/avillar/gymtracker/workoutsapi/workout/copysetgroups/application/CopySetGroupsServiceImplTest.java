package org.avillar.gymtracker.workoutsapi.workout.copysetgroups.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang3.NotImplementedException;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.CopySetGroupsServiceImpl;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.mapper.CopySetGroupsServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.model.CopySetGroupsResponseApplication;
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
    final Workout workoutDestination = easyRandom.nextObject(Workout.class);
    final Workout workoutSource = easyRandom.nextObject(Workout.class);

    final SetGroup setSource0 = easyRandom.nextObject(SetGroup.class);
    setSource0.setListOrder(0);
    setSource0.setWorkout(workoutDestination);
    final org.avillar.gymtracker.workoutapi.domain.Set set01 =
        easyRandom.nextObject(org.avillar.gymtracker.workoutapi.domain.Set.class);
    set01.setListOrder(0);
    set01.setSetGroup(setSource0);
    setSource0.setSets(Set.of(set01));
    workoutDestination.setSetGroups(Set.of(setSource0));

    final SetGroup setSource1 = easyRandom.nextObject(SetGroup.class);
    setSource1.setListOrder(0);
    setSource1.setWorkout(workoutSource);
    final org.avillar.gymtracker.workoutapi.domain.Set set11 =
        easyRandom.nextObject(org.avillar.gymtracker.workoutapi.domain.Set.class);
    set11.setListOrder(0);
    set11.setSetGroup(setSource1);
    setSource1.setSets(Set.of(set11));
    final SetGroup setSource2 = easyRandom.nextObject(SetGroup.class);
    setSource2.setListOrder(1);
    setSource2.setWorkout(workoutSource);
    final org.avillar.gymtracker.workoutapi.domain.Set set21 =
        easyRandom.nextObject(org.avillar.gymtracker.workoutapi.domain.Set.class);
    set21.setListOrder(0);
    set21.setSetGroup(setSource2);
    setSource2.setSets(Set.of(set21));
    workoutSource.setSetGroups(Set.of(setSource1, setSource2));

    when(workoutDao.getFullWorkoutByIds(List.of(workoutDestination.getId(), workoutSource.getId())))
        .thenReturn(Set.of(workoutDestination, workoutSource));
    doNothing().when(authWorkoutsService).checkAccess(workoutDestination, AuthOperations.UPDATE);
    doNothing().when(authWorkoutsService).checkAccess(workoutSource, AuthOperations.READ);

    final List<CopySetGroupsResponseApplication> result =
        copySetGroupsService.execute(workoutDestination.getId(), workoutSource.getId(), true);
    assertEquals(
        workoutDestination.getSetGroups().size() + workoutSource.getSetGroups().size(),
        result.size());
    assertEquals(
        setSource0.getListOrder(),
        result.stream()
            .filter(sg -> sg.getDescription().equals(setSource0.getDescription()))
            .findAny()
            .get()
            .getListOrder());
    assertEquals(
        setSource1.getListOrder() + 1,
        result.stream()
            .filter(sg -> sg.getDescription().equals(setSource1.getDescription()))
            .findAny()
            .get()
            .getListOrder());
    assertEquals(
        setSource2.getListOrder() + 1,
        result.stream()
            .filter(sg -> sg.getDescription().equals(setSource2.getDescription()))
            .findAny()
            .get()
            .getListOrder());

    // TODO Mejorar el test
    verify(setGroupDao).saveAll(any());
    verify(setDao).saveAll(any());
  }

  @Test
  void notImplemented() {
    Assertions.assertThrows(
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
        .thenReturn(Set.of(workoutDestination));

    final EntityNotFoundException exception =
        Assertions.assertThrows(
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
        .thenReturn(Set.of(workoutSource));

    final EntityNotFoundException exception =
        Assertions.assertThrows(
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
    final AuthOperations authOperations = AuthOperations.UPDATE;

    when(workoutDao.getFullWorkoutByIds(List.of(workoutDestination.getId(), workoutSource.getId())))
        .thenReturn(Set.of(workoutDestination, workoutSource));
    doThrow(new IllegalAccessException(workoutDestination, authOperations, userId))
        .when(authWorkoutsService)
        .checkAccess(workoutDestination, authOperations);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () ->
                copySetGroupsService.execute(
                    workoutDestination.getId(), workoutSource.getId(), true));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workoutDestination.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperations, exception.getAuthOperations());
    verify(workoutDao, never()).save(Mockito.any(Workout.class));
  }

  @Test
  void sourceNotPermission() {
    final Workout workoutDestination = easyRandom.nextObject(Workout.class);
    final Workout workoutSource = easyRandom.nextObject(Workout.class);
    final UUID userId = UUID.randomUUID();
    final AuthOperations authOperations = AuthOperations.READ;

    when(workoutDao.getFullWorkoutByIds(List.of(workoutDestination.getId(), workoutSource.getId())))
        .thenReturn(Set.of(workoutDestination, workoutSource));
    doNothing().when(authWorkoutsService).checkAccess(workoutDestination, AuthOperations.UPDATE);
    doThrow(new IllegalAccessException(workoutSource, authOperations, userId))
        .when(authWorkoutsService)
        .checkAccess(workoutSource, authOperations);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () ->
                copySetGroupsService.execute(
                    workoutDestination.getId(), workoutSource.getId(), true));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workoutSource.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperations, exception.getAuthOperations());
    verify(workoutDao, never()).save(Mockito.any(Workout.class));
  }
}
