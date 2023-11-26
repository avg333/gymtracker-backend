package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.ListOrderNotValidException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
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
class UpdateSetGroupListOrderServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.UPDATE;

  @InjectMocks private UpdateSetGroupListOrderServiceImpl updateSetGroupListOrderService;

  @Mock private SetGroupFacade setGroupFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Mock private EntitySorter entitySorter;

  private static List<SetGroup> getSetGroups() {
    final Workout workout = Instancio.create(Workout.class);
    final List<SetGroup> setGroups = Instancio.createList(SetGroup.class);
    for (int i = 0; i < setGroups.size(); i++) {
      final SetGroup setGroup = setGroups.get(i);
      setGroup.setListOrder(i);
      setGroup.setWorkout(workout);
      setGroup.getSets().forEach(set -> set.setSetGroup(setGroup));
    }
    return setGroups;
  }

  @Test
  void shouldUpdateSetGroupListOrderWithReorderSuccessfully()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException, ListOrderNotValidException {
    final List<SetGroup> setGroups = getSetGroups();
    final int listOrder = setGroups.size() - 1;
    final SetGroup firstSetGroup = setGroups.get(0);
    final List<SetGroup> savedSetGroups = getSetGroups();

    final ArgumentCaptor<List<SetGroup>> setGroupsCaptorBeforeSort =
        ArgumentCaptor.forClass(List.class);
    final ArgumentCaptor<SetGroup> setGroupCaptorBeforeSort =
        ArgumentCaptor.forClass(SetGroup.class);
    final ArgumentCaptor<List<SetGroup>> setGroupsCaptorBeforeSave =
        ArgumentCaptor.forClass(List.class);

    when(setGroupFacade.getSetGroupWithWorkout(firstSetGroup.getId())).thenReturn(firstSetGroup);
    doNothing().when(authWorkoutsService).checkAccess(firstSetGroup, AUTH_OPERATIONS);
    when(setGroupFacade.getSetGroupsByWorkoutId(firstSetGroup.getWorkout().getId()))
        .thenReturn(setGroups);
    doNothing()
        .when(entitySorter)
        .sortUpdate(setGroupsCaptorBeforeSort.capture(), setGroupCaptorBeforeSort.capture());
    when(setGroupFacade.saveSetGroups(setGroupsCaptorBeforeSave.capture()))
        .thenReturn(savedSetGroups);

    assertThat(updateSetGroupListOrderService.execute(firstSetGroup.getId(), listOrder))
        .isEqualTo(savedSetGroups);
    // FIXME
    final List<SetGroup> setGroupListBeforeSort = setGroupsCaptorBeforeSort.getValue();
    assertThat(
            setGroupListBeforeSort.stream().filter(sg -> !sg.getId().equals(firstSetGroup.getId())))
        .isEqualTo(
            setGroups.stream()
                .filter(sg -> !sg.getId().equals(firstSetGroup.getId()))
                .collect(Collectors.toList()));
    assertThat(setGroupListBeforeSort.get(0).getListOrder()).isEqualTo(listOrder);

    final List<SetGroup> setGroupListBeforeSave = setGroupsCaptorBeforeSave.getValue();
    assertThat(setGroupListBeforeSave).isEqualTo(setGroupListBeforeSort);

    verify(entitySorter).sortUpdate(setGroupListBeforeSort, firstSetGroup);
    verify(setGroupFacade).saveSetGroups(setGroupListBeforeSave);
  }

  @Test
  void shouldUpdateSetGroupListOrderWithoutReorderSuccessfully()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException, ListOrderNotValidException {
    final List<SetGroup> setGroups = getSetGroups();
    final SetGroup setGroupLast = setGroups.get(setGroups.size() - 1);
    final int listOrder = setGroupLast.getListOrder();

    when(setGroupFacade.getSetGroupWithWorkout(setGroupLast.getId())).thenReturn(setGroupLast);
    doNothing().when(authWorkoutsService).checkAccess(setGroupLast, AUTH_OPERATIONS);
    when(setGroupFacade.getSetGroupsByWorkoutId(setGroupLast.getWorkout().getId()))
        .thenReturn(setGroups);

    assertThat(updateSetGroupListOrderService.execute(setGroupLast.getId(), listOrder))
        .isEqualTo(setGroups);

    verify(entitySorter, never()).sortUpdate(any(), any());
    verify(setGroupFacade, never()).saveSetGroups(any());
  }

  @Test
  void shouldThrowListOrderNotValidExceptionWhenListOrderIsNotValid()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final List<SetGroup> setGroups = getSetGroups();
    final SetGroup setGroupLast = setGroups.get(setGroups.size() - 1);
    final int listOrder = -1;
    final ListOrderNotValidException exception =
        new ListOrderNotValidException(listOrder, 0, setGroups.size());

    when(setGroupFacade.getSetGroupWithWorkout(setGroupLast.getId())).thenReturn(setGroupLast);
    doNothing().when(authWorkoutsService).checkAccess(setGroupLast, AUTH_OPERATIONS);
    when(setGroupFacade.getSetGroupsByWorkoutId(setGroupLast.getWorkout().getId()))
        .thenReturn(setGroups);

    assertThatThrownBy(
            () -> updateSetGroupListOrderService.execute(setGroupLast.getId(), listOrder))
        .isEqualTo(exception);

    verify(entitySorter, never()).sortUpdate(any(), any());
    verify(setGroupFacade, never()).saveSetGroups(any());
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToUpdateSetGroup()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final int listOrder = 0;
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(setGroupFacade.getSetGroupWithWorkout(setGroup.getId())).thenReturn(setGroup);
    doThrow(exception).when(authWorkoutsService).checkAccess(setGroup, AUTH_OPERATIONS);

    assertThatThrownBy(() -> updateSetGroupListOrderService.execute(setGroup.getId(), listOrder))
        .isEqualTo(exception);

    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setGroupFacade, never()).saveSetGroup(any());
  }

  @Test
  void shouldThrowSetGroupNotFoundExceptionWhenSetGroupNotFound() throws SetGroupNotFoundException {
    final UUID setGroupId = UUID.randomUUID();
    final int listOrder = 0;
    final SetGroupNotFoundException exception =
        ExceptionGenerator.generateSetGroupNotFoundException();

    doThrow(exception).when(setGroupFacade).getSetGroupWithWorkout(setGroupId);

    assertThatThrownBy(() -> updateSetGroupListOrderService.execute(setGroupId, listOrder))
        .isEqualTo(exception);

    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setGroupFacade, never()).saveSetGroup(any());
  }
}
