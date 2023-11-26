package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application;

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
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.ListOrderNotValidException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.set.SetFacade;
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
class UpdateSetListOrderServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.UPDATE;

  @InjectMocks private UpdateSetListOrderServiceImpl updateSetListOrderService;

  @Mock private SetFacade setFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Mock private EntitySorter entitySorter;

  private static List<Set> getSets() {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final List<Set> sets = Instancio.createList(Set.class);
    for (int i = 0; i < sets.size(); i++) {
      final Set set = sets.get(i);
      set.setListOrder(i);
      set.setSetGroup(setGroup);
    }
    return sets;
  }

  @Test
  void shouldUpdateSetListOrderWithReorderSuccessfully()
      throws SetNotFoundException, WorkoutIllegalAccessException, ListOrderNotValidException {
    final List<Set> sets = getSets();
    final int listOrder = sets.size() - 1;
    final Set firstSet = sets.get(0);
    final List<Set> savedSets = getSets();

    final ArgumentCaptor<List<Set>> setsCaptorBeforeSort = ArgumentCaptor.forClass(List.class);
    final ArgumentCaptor<Set> setCaptorBeforeSort = ArgumentCaptor.forClass(Set.class);
    final ArgumentCaptor<List<Set>> setsCaptorBeforeSave = ArgumentCaptor.forClass(List.class);

    when(setFacade.getSetFull(firstSet.getId())).thenReturn(firstSet);
    doNothing().when(authWorkoutsService).checkAccess(firstSet, AUTH_OPERATIONS);
    when(setFacade.getSetsBySetGroupId(firstSet.getSetGroup().getId())).thenReturn(sets);
    doNothing()
        .when(entitySorter)
        .sortUpdate(setsCaptorBeforeSort.capture(), setCaptorBeforeSort.capture());
    when(setFacade.saveSets(setsCaptorBeforeSave.capture())).thenReturn(savedSets);

    assertThat(updateSetListOrderService.execute(firstSet.getId(), listOrder)).isEqualTo(savedSets);
    // FIXME
    final List<Set> setGroupsBeforeSort = setsCaptorBeforeSort.getValue();
    assertThat(setGroupsBeforeSort).isEqualTo(sets);

    final Set setBeforeSort = setCaptorBeforeSort.getValue();
    assertThat(setBeforeSort)
        .usingRecursiveComparison()
        .ignoringFields("listOrder")
        .isEqualTo(firstSet);
    assertThat(setBeforeSort.getListOrder()).isEqualTo(listOrder);

    final List<Set> setGroupsBeforeSave = setsCaptorBeforeSave.getValue();
    assertThat(setGroupsBeforeSave).isEqualTo(setGroupsBeforeSort);

    verify(entitySorter).sortUpdate(setGroupsBeforeSort, firstSet);
    verify(setFacade).saveSets(setGroupsBeforeSave);
  }

  @Test
  void shouldUpdateSetListOrderWithoutReorderSuccessfully()
      throws SetNotFoundException, WorkoutIllegalAccessException, ListOrderNotValidException {
    final List<Set> sets = getSets();
    final Set lastSet = sets.get(sets.size() - 1);
    final int listOrder = lastSet.getListOrder();

    when(setFacade.getSetFull(lastSet.getId())).thenReturn(lastSet);
    doNothing().when(authWorkoutsService).checkAccess(lastSet, AUTH_OPERATIONS);
    when(setFacade.getSetsBySetGroupId(lastSet.getSetGroup().getId())).thenReturn(sets);

    assertThat(updateSetListOrderService.execute(lastSet.getId(), listOrder)).isEqualTo(sets);

    verify(entitySorter, never()).sortUpdate(any(), any());
    verify(setFacade, never()).saveSets(any());
  }

  @Test
  void shouldThrowListOrderNotValidExceptionWhenListOrderIsNotValid()
      throws SetNotFoundException, WorkoutIllegalAccessException {
    final List<Set> sets = getSets();
    final Set lastSet = sets.get(sets.size() - 1);
    final int listOrder = -1;
    final ListOrderNotValidException exception =
        new ListOrderNotValidException(listOrder, 0, sets.size());

    when(setFacade.getSetFull(lastSet.getId())).thenReturn(lastSet);
    doNothing().when(authWorkoutsService).checkAccess(lastSet, AUTH_OPERATIONS);
    when(setFacade.getSetsBySetGroupId(lastSet.getSetGroup().getId())).thenReturn(sets);

    assertThatThrownBy(() -> updateSetListOrderService.execute(lastSet.getId(), listOrder))
        .isEqualTo(exception);

    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setFacade, never()).saveSets(any());
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToUpdateSetListOrder()
      throws SetNotFoundException, WorkoutIllegalAccessException {
    final Set set = Instancio.create(Set.class);
    final int listOrder = 0;
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(setFacade.getSetFull(set.getId())).thenReturn(set);
    doThrow(exception).when(authWorkoutsService).checkAccess(set, AUTH_OPERATIONS);

    assertThatThrownBy(() -> updateSetListOrderService.execute(set.getId(), listOrder))
        .isEqualTo(exception);

    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setFacade, never()).saveSets(any());
  }

  @Test
  void shouldThrowSetNotFoundExceptionWhenSetNotFound() throws SetNotFoundException {
    final UUID setId = UUID.randomUUID();
    final int listOrder = 0;
    final SetNotFoundException exception = ExceptionGenerator.generateSetNotFoundException();

    doThrow(exception).when(setFacade).getSetFull(setId);

    assertThatThrownBy(() -> updateSetListOrderService.execute(setId, listOrder))
        .isEqualTo(exception);

    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setFacade, never()).saveSets(any());
  }
}
