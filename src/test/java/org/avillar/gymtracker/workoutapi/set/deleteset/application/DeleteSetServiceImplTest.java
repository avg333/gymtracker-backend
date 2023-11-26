package org.avillar.gymtracker.workoutapi.set.deleteset.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
class DeleteSetServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.DELETE;

  @InjectMocks private DeleteSetServiceImpl deleteSetService;

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
  void shouldDeleteSetWithReorderSuccessfully()
      throws SetNotFoundException, WorkoutIllegalAccessException {
    final List<Set> sets = getSets();
    final Set firstSet = sets.get(0);
    final List<Set> savedSets = getSets();

    final ArgumentCaptor<List<Set>> setsBeforeOrderCaptor = ArgumentCaptor.forClass(List.class);
    final ArgumentCaptor<List<Set>> setsBeforeSaveCaptor = ArgumentCaptor.forClass(List.class);

    when(setFacade.getSetFull(firstSet.getId())).thenReturn(firstSet);
    doNothing().when(authWorkoutsService).checkAccess(firstSet, AUTH_OPERATIONS);
    when(setFacade.getSetsBySetGroupId(firstSet.getSetGroup().getId())).thenReturn(sets);
    doNothing().when(setFacade).deleteSet(firstSet.getId());
    doNothing().when(entitySorter).sortDelete(setsBeforeOrderCaptor.capture(), eq(firstSet));
    when(setFacade.saveSets(setsBeforeSaveCaptor.capture())).thenReturn(savedSets);

    assertThatNoException().isThrownBy(() -> deleteSetService.execute(firstSet.getId()));

    final List<Set> setsBeforeOrder = setsBeforeOrderCaptor.getValue();
    assertThat(setsBeforeOrder).isNotNull().usingRecursiveComparison().isEqualTo(sets);
    final List<Set> setsBeforeSave = setsBeforeSaveCaptor.getValue();
    assertThat(setsBeforeSave).isNotNull().usingRecursiveComparison().isEqualTo(setsBeforeOrder);

    verify(setFacade).deleteSet(firstSet.getId());
    verify(entitySorter).sortDelete(setsBeforeOrder, firstSet);
    verify(setFacade).saveSets(setsBeforeSave);
  }

  @Test
  void shouldDeleteSetWithoutReorderSuccessfully()
      throws SetNotFoundException, WorkoutIllegalAccessException {
    final List<Set> sets = getSets();
    final Set lastSet = sets.get(sets.size() - 1);

    when(setFacade.getSetFull(lastSet.getId())).thenReturn(lastSet);
    doNothing().when(authWorkoutsService).checkAccess(lastSet, AUTH_OPERATIONS);
    when(setFacade.getSetsBySetGroupId(lastSet.getSetGroup().getId())).thenReturn(sets);
    doNothing().when(setFacade).deleteSet(lastSet.getId());

    assertThatNoException().isThrownBy(() -> deleteSetService.execute(lastSet.getId()));

    verify(setFacade).deleteSet(lastSet.getId());
    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setFacade, never()).saveSets(any());
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToDeleteSet()
      throws SetNotFoundException, WorkoutIllegalAccessException {
    final Set set = Instancio.create(Set.class);
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(setFacade.getSetFull(set.getId())).thenReturn(set);
    doThrow(exception).when(authWorkoutsService).checkAccess(set, AUTH_OPERATIONS);

    assertThatThrownBy(() -> deleteSetService.execute(set.getId())).isEqualTo(exception);

    verify(setFacade, never()).deleteSet(any());
    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setFacade, never()).saveSets(any());
  }

  @Test
  void shouldThrowSetNotFoundExceptionWhenSetIsNotFound() throws SetNotFoundException {
    final UUID setId = UUID.randomUUID();
    final SetNotFoundException exception = ExceptionGenerator.generateSetNotFoundException();

    doThrow(exception).when(setFacade).getSetFull(setId);

    assertThatThrownBy(() -> deleteSetService.execute(setId)).isEqualTo(exception);

    verify(setFacade, never()).deleteSet(any());
    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setFacade, never()).saveSets(any());
  }
}
