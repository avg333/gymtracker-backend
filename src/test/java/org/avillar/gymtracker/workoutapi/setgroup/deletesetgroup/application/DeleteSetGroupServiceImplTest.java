package org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.exercise.ExercisesFacade;
import org.avillar.gymtracker.workoutapi.common.facade.setgroup.SetGroupFacade;
import org.avillar.gymtracker.workoutapi.common.sort.application.EntitySorter;
import org.avillar.gymtracker.workoutapi.common.utils.ExceptionGenerator;
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
class DeleteSetGroupServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.DELETE;

  @InjectMocks private DeleteSetGroupServiceImpl deleteSetGroupService;

  @Mock private SetGroupFacade setGroupFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Mock private EntitySorter entitySorter;
  @Mock private ExercisesFacade exercisesFacade;

  private static List<SetGroup> getSetGroups() {
    final Workout workout = Instancio.create(Workout.class);
    final List<SetGroup> setGroups = Instancio.createList(SetGroup.class);
    for (int i = 0; i < setGroups.size(); i++) {
      final SetGroup setGroup = setGroups.get(i);
      setGroup.setListOrder(i);
      setGroup.setWorkout(workout);
      setGroup.setSets(Instancio.createList(Set.class));
      setGroup.getSets().forEach(set -> set.setSetGroup(setGroup));
    }
    return setGroups;
  }

  @Test
  void shouldDeleteSetGroupSuccessfullyWithReorder()
      throws SetGroupNotFoundException,
          WorkoutIllegalAccessException,
          ExerciseUnavailableException {
    final List<SetGroup> setGroups = getSetGroups();
    final List<SetGroup> setGroupsSaved = getSetGroups();
    final SetGroup firstSetGroup = setGroups.get(0);

    final ArgumentCaptor<List<SetGroup>> setGroupsBeforeOrderCaptor =
        ArgumentCaptor.forClass(List.class);
    final ArgumentCaptor<List<SetGroup>> setGroupsBeforeSaveCaptor =
        ArgumentCaptor.forClass(List.class);

    when(setGroupFacade.getSetGroupWithWorkout(firstSetGroup.getId())).thenReturn(firstSetGroup);
    doNothing().when(authWorkoutsService).checkAccess(firstSetGroup, AUTH_OPERATIONS);
    when(exercisesFacade.decrementExerciseUses(
            firstSetGroup.getWorkout().getUserId(), firstSetGroup.getExerciseId()))
        .thenReturn(Instancio.create(Integer.class));
    when(setGroupFacade.getSetGroupsByWorkoutId(firstSetGroup.getWorkout().getId()))
        .thenReturn(setGroups);
    doNothing().when(setGroupFacade).deleteSetGroup(firstSetGroup.getId());
    doNothing()
        .when(entitySorter)
        .sortDelete(setGroupsBeforeOrderCaptor.capture(), eq(firstSetGroup));
    when(setGroupFacade.saveSetGroups(setGroupsBeforeSaveCaptor.capture()))
        .thenReturn(setGroupsSaved);

    assertDoesNotThrow(() -> deleteSetGroupService.execute(firstSetGroup.getId()));

    final List<SetGroup> setGroupsBeforeOrder = setGroupsBeforeOrderCaptor.getValue();
    assertThat(setGroupsBeforeOrder).isNotNull().usingRecursiveComparison().isEqualTo(setGroups);
    final List<SetGroup> setGroupsBeforeSave = setGroupsBeforeSaveCaptor.getValue();
    assertThat(setGroupsBeforeSave)
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(setGroupsBeforeOrder);

    verify(exercisesFacade)
        .decrementExerciseUses(
            firstSetGroup.getWorkout().getUserId(), firstSetGroup.getExerciseId());
    verify(setGroupFacade).deleteSetGroup(firstSetGroup.getId());
    verify(entitySorter).sortDelete(setGroupsBeforeOrder, firstSetGroup);
    verify(setGroupFacade).saveSetGroups(setGroupsBeforeSave);
  }

  @Test
  void shouldDeleteSetGroupSuccessfullyWithoutReorder()
      throws SetGroupNotFoundException,
          WorkoutIllegalAccessException,
          ExerciseUnavailableException {
    final List<SetGroup> setGroups = getSetGroups();
    final SetGroup setGroupLast = setGroups.get(setGroups.size() - 1);

    when(setGroupFacade.getSetGroupWithWorkout(setGroupLast.getId())).thenReturn(setGroupLast);
    doNothing().when(authWorkoutsService).checkAccess(setGroupLast, AUTH_OPERATIONS);
    when(exercisesFacade.decrementExerciseUses(
            setGroupLast.getWorkout().getUserId(), setGroupLast.getExerciseId()))
        .thenReturn(Instancio.create(Integer.class));
    when(setGroupFacade.getSetGroupsByWorkoutId(setGroupLast.getWorkout().getId()))
        .thenReturn(setGroups);

    assertDoesNotThrow(() -> deleteSetGroupService.execute(setGroupLast.getId()));

    verify(exercisesFacade)
        .decrementExerciseUses(setGroupLast.getWorkout().getUserId(), setGroupLast.getExerciseId());
    verify(setGroupFacade).deleteSetGroup(setGroupLast.getId());
    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setGroupFacade, never()).saveSetGroup(any());
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToDeleteSetGroup()
      throws SetGroupNotFoundException,
          WorkoutIllegalAccessException,
          ExerciseUnavailableException {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(setGroupFacade.getSetGroupWithWorkout(setGroup.getId())).thenReturn(setGroup);
    doThrow(exception).when(authWorkoutsService).checkAccess(setGroup, AUTH_OPERATIONS);

    assertThatThrownBy(() -> deleteSetGroupService.execute(setGroup.getId())).isEqualTo(exception);

    verify(exercisesFacade, never()).decrementExerciseUses(any(), any());
    verify(setGroupFacade, never()).deleteSetGroup(any());
    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setGroupFacade, never()).saveSetGroup(any());
  }

  @Test
  void shouldThrowSetGroupNotFoundExceptionWhenSetGroupIsNotFound()
      throws SetGroupNotFoundException, ExerciseUnavailableException {
    final UUID setGroupId = UUID.randomUUID();
    final SetGroupNotFoundException exception =
        ExceptionGenerator.generateSetGroupNotFoundException();

    doThrow(exception).when(setGroupFacade).getSetGroupWithWorkout(setGroupId);

    assertThatThrownBy(() -> deleteSetGroupService.execute(setGroupId)).isEqualTo(exception);

    verify(exercisesFacade, never()).decrementExerciseUses(any(), any());
    verify(setGroupFacade, never()).deleteSetGroup(any());
    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setGroupFacade, never()).saveSetGroup(any());
  }
}
