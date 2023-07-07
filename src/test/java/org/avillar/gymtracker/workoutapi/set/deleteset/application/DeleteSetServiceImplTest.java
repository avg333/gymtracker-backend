package org.avillar.gymtracker.workoutapi.set.deleteset.application;

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
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.common.sort.application.EntitySorter;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteSetServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private DeleteSetServiceImpl deleteSetService;

  @Mock private SetDao setDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Mock private EntitySorter entitySorter;

  @Test
  void deleteOk() {
    final List<Set> sets = getSets();
    final Set secondSet = sets.get(1);

    when(setDao.getSetFullById(secondSet.getId())).thenReturn(List.of(secondSet));
    doNothing().when(authWorkoutsService).checkAccess(secondSet, AuthOperations.DELETE);
    when(setDao.getSetsBySetGroupId(secondSet.getSetGroup().getId())).thenReturn(sets);
    doNothing().when(entitySorter).sortDelete(sets, secondSet);

    assertDoesNotThrow(() -> deleteSetService.execute(secondSet.getId()));
    verify(setDao).deleteById(secondSet.getId());
    verify(entitySorter).sortDelete(sets, secondSet); // FIXME
    verify(setDao).saveAll(sets); // FIXME Se deberia llamar sin la set eliminada
  }

  @Test
  void deleteOkNoReorder() {
    final List<Set> sets = getSets();
    final Set lastSet = sets.get(sets.size() - 1);

    when(setDao.getSetFullById(lastSet.getId())).thenReturn(List.of(lastSet));
    doNothing().when(authWorkoutsService).checkAccess(lastSet, AuthOperations.DELETE);
    when(setDao.getSetsBySetGroupId(lastSet.getSetGroup().getId())).thenReturn(sets);

    assertDoesNotThrow(() -> deleteSetService.execute(lastSet.getId()));
    verify(setDao).deleteById(lastSet.getId());
    verify(entitySorter, never()).sortDelete(sets, lastSet);
    verify(setDao, never()).saveAll(sets);
  }

  private List<Set> getSets() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final List<Set> sets = easyRandom.objects(Set.class, 5).toList();
    for (int i = 0; i < sets.size(); i++) {
      final Set set = sets.get(i);
      set.setListOrder(i);
      set.setSetGroup(setGroup);
    }
    return sets;
  }

  @Test
  void deleteNotFound() {
    final UUID setId = UUID.randomUUID();

    when(setDao.getSetFullById(setId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        assertThrows(EntityNotFoundException.class, () -> deleteSetService.execute(setId));
    assertEquals(Set.class.getSimpleName(), exception.getClassName());
    assertEquals(setId, exception.getId());
    verify(setDao, never()).deleteById(any());
    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setDao, never()).saveAll(any());
  }

  @Test
  void deleteNotPermission() {
    final Set set = easyRandom.nextObject(Set.class);
    final UUID userId = UUID.randomUUID();
    final AuthOperations deleteOperation = AuthOperations.DELETE;

    when(setDao.getSetFullById(set.getId())).thenReturn(List.of(set));
    doThrow(new IllegalAccessException(set, deleteOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(set, deleteOperation);

    final IllegalAccessException exception =
        assertThrows(IllegalAccessException.class, () -> deleteSetService.execute(set.getId()));
    assertEquals(Set.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(set.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(deleteOperation, exception.getAuthOperations());
    verify(setDao, never()).deleteById(any());
    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setDao, never()).saveAll(any());
  }
}
