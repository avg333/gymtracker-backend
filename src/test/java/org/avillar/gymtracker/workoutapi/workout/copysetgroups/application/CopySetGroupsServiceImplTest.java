package org.avillar.gymtracker.workoutapi.workout.copysetgroups.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.NotImplementedException;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.set.SetFacade;
import org.avillar.gymtracker.workoutapi.common.facade.setgroup.SetGroupFacade;
import org.avillar.gymtracker.workoutapi.common.facade.workout.WorkoutFacade;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.CopySetGroupsServiceImpl.CopySetGroupsRequest;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.CopySetGroupsServiceImpl.CopySetGroupsRequest.Source;
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
class CopySetGroupsServiceImplTest {

  @InjectMocks private CopySetGroupsServiceImpl copySetGroupsService;

  @Mock private WorkoutFacade workoutFacade;
  @Mock private SetGroupFacade setGroupFacade;
  @Mock private SetFacade setFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  private static Workout getWorkout() {
    final Workout workout = Instancio.create(Workout.class);
    final List<SetGroup> setGroups = Instancio.createList(SetGroup.class);
    for (int i = 0; i < setGroups.size(); i++) {
      final SetGroup setGroup = setGroups.get(i);
      setGroup.setListOrder(i);
      setGroup.setWorkout(workout);
      final List<Set> sets = Instancio.createList(Set.class);
      for (int j = 0; j < sets.size(); j++) {
        final Set set = sets.get(j);
        set.setListOrder(j);
        set.setSetGroup(setGroup);
      }
      setGroup.setSets(sets);
    }
    workout.setSetGroups(setGroups);
    return workout;
  }

  @Test
  void copyOk() throws WorkoutIllegalAccessException, WorkoutNotFoundException {
    final Workout workoutDestination = getWorkout();
    final Workout workoutSource = getWorkout();
    final CopySetGroupsRequest request =
        CopySetGroupsRequest.builder().id(workoutSource.getId()).source(Source.WORKOUT).build();

    when(workoutFacade.getFullWorkoutsByIds(
            List.of(workoutDestination.getId(), workoutSource.getId())))
        .thenReturn(List.of(workoutDestination, workoutSource));
    doNothing().when(authWorkoutsService).checkAccess(workoutDestination, AuthOperations.UPDATE);
    doNothing().when(authWorkoutsService).checkAccess(workoutSource, AuthOperations.READ);

    final List<SetGroup> result = copySetGroupsService.execute(workoutDestination.getId(), request);

    assertThat(result).isNotNull();
    // assertThat(result).usingRecursiveComparison().isEqualTo(expected);TODO Finish tests
    verify(setGroupFacade).saveSetGroups(any());
    verify(setFacade).saveSets(any());
  }

  @Test
  void notImplemented() {
    final UUID workoutSourceId = UUID.randomUUID();
    final CopySetGroupsRequest request =
        CopySetGroupsRequest.builder().id(UUID.randomUUID()).source(Source.SESSION).build();

    assertThrows(
        NotImplementedException.class,
        () -> copySetGroupsService.execute(workoutSourceId, request));
    verify(setGroupFacade, never()).saveSetGroups(any());
    verify(setFacade, never()).saveSets(any());
  }

  @Test
  void sourceNotFound() {
    final Workout workoutDestination = Instancio.create(Workout.class);
    final UUID workoutSourceId = UUID.randomUUID();
    final CopySetGroupsRequest request =
        CopySetGroupsRequest.builder().id(workoutSourceId).source(Source.WORKOUT).build();

    when(workoutFacade.getFullWorkoutsByIds(List.of(workoutDestination.getId(), workoutSourceId)))
        .thenReturn(List.of(workoutDestination));

    final EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class,
            () -> copySetGroupsService.execute(workoutDestination.getId(), request));
    assertEquals(Workout.class.getSimpleName(), exception.getClassName());
    assertEquals(workoutSourceId, exception.getId());
    verify(setGroupFacade, never()).saveSetGroups(any());
    verify(setFacade, never()).saveSets(any());
  }

  @Test
  void destinationNotFound() {
    final Workout workoutSource = Instancio.create(Workout.class);
    final UUID workoutDestinationId = UUID.randomUUID();
    final CopySetGroupsRequest request =
        CopySetGroupsRequest.builder().id(workoutSource.getId()).source(Source.WORKOUT).build();

    when(workoutFacade.getFullWorkoutsByIds(List.of(workoutDestinationId, workoutSource.getId())))
        .thenReturn(List.of(workoutSource));

    final EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class,
            () -> copySetGroupsService.execute(workoutDestinationId, request));
    assertEquals(Workout.class.getSimpleName(), exception.getClassName());
    assertEquals(workoutDestinationId, exception.getId());
    verify(setGroupFacade, never()).saveSetGroups(any());
    verify(setFacade, never()).saveSets(any());
  }

  @Test
  void destinationNotPermission() throws WorkoutIllegalAccessException {
    final Workout workoutDestination = Instancio.create(Workout.class);
    final Workout workoutSource = Instancio.create(Workout.class);
    final UUID userId = UUID.randomUUID();
    final AuthOperations updateOperation = AuthOperations.UPDATE;
    final CopySetGroupsRequest request =
        CopySetGroupsRequest.builder().id(workoutSource.getId()).source(Source.WORKOUT).build();

    when(workoutFacade.getFullWorkoutsByIds(
            List.of(workoutDestination.getId(), workoutSource.getId())))
        .thenReturn(List.of(workoutDestination, workoutSource));
    doThrow(new WorkoutIllegalAccessException(workoutDestination.getId(), updateOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(workoutDestination, updateOperation);

    final WorkoutIllegalAccessException exception =
        assertThrows(
            WorkoutIllegalAccessException.class,
            () -> copySetGroupsService.execute(workoutDestination.getId(), request));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workoutDestination.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(updateOperation, exception.getAuthOperations());
    verify(setGroupFacade, never()).saveSetGroups(any());
    verify(setFacade, never()).saveSets(any());
  }

  @Test
  void sourceNotPermission() throws WorkoutIllegalAccessException {
    final Workout workoutDestination = Instancio.create(Workout.class);
    final Workout workoutSource = Instancio.create(Workout.class);
    final UUID userId = UUID.randomUUID();
    final AuthOperations readOperation = AuthOperations.READ;
    final CopySetGroupsRequest request =
        CopySetGroupsRequest.builder().id(workoutSource.getId()).source(Source.WORKOUT).build();

    when(workoutFacade.getFullWorkoutsByIds(
            List.of(workoutDestination.getId(), workoutSource.getId())))
        .thenReturn(List.of(workoutDestination, workoutSource));
    doNothing().when(authWorkoutsService).checkAccess(workoutDestination, AuthOperations.UPDATE);
    doThrow(new WorkoutIllegalAccessException(workoutSource.getId(), readOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(workoutSource, readOperation);

    final WorkoutIllegalAccessException exception =
        assertThrows(
            WorkoutIllegalAccessException.class,
            () -> copySetGroupsService.execute(workoutDestination.getId(), request));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workoutSource.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(readOperation, exception.getAuthOperations());
    verify(setGroupFacade, never()).saveSetGroups(any());
    verify(setFacade, never()).saveSets(any());
  }
}
