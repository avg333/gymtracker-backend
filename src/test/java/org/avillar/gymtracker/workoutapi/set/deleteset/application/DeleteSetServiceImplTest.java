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
import org.junit.jupiter.api.Assertions;
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
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final Set setFirst = new Set(null, 1, 1.0, 1.0, setGroup);
    setFirst.setListOrder(0);
    setFirst.setId(UUID.randomUUID());
    final Set setSecond = new Set(null, 1, 1.0, 1.0, setGroup);
    setSecond.setListOrder(1);
    setSecond.setId(UUID.randomUUID());
    setGroup.setSets(java.util.Set.of(setFirst, setSecond));
    final List<Set> sets = setGroup.getSets().stream().toList();

    when(setDao.getSetFullById(setFirst.getId())).thenReturn(List.of(setFirst));
    doNothing().when(authWorkoutsService).checkAccess(setFirst, AuthOperations.DELETE);
    when(setDao.getSetsBySetGroupId(setGroup.getId())).thenReturn(sets);
    doNothing().when(entitySorter).sortDelete(sets, setFirst); // FIXME

    assertDoesNotThrow(() -> deleteSetService.execute(setFirst.getId()));
    verify(setDao).deleteById(setFirst.getId());
    verify(entitySorter).sortDelete(sets, setFirst);
    verify(setDao).saveAll(sets);
  }

  @Test
  void deleteOkNoReorder() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final Set setFirst = new Set(null, 1, 1.0, 1.0, setGroup);
    setFirst.setListOrder(0);
    setFirst.setId(UUID.randomUUID());
    final Set setSecond = new Set(null, 1, 1.0, 1.0, setGroup);
    setSecond.setListOrder(1);
    setSecond.setId(UUID.randomUUID());
    setGroup.setSets(java.util.Set.of(setFirst, setSecond));
    final List<Set> sets = setGroup.getSets().stream().toList();

    when(setDao.getSetFullById(setSecond.getId())).thenReturn(List.of(setSecond));
    doNothing().when(authWorkoutsService).checkAccess(setSecond, AuthOperations.DELETE);
    when(setDao.getSetsBySetGroupId(setGroup.getId())).thenReturn(sets);

    assertDoesNotThrow(() -> deleteSetService.execute(setSecond.getId()));
    verify(setDao).deleteById(setSecond.getId());
    verify(entitySorter, never()).sortDelete(sets, setSecond);
    verify(setDao, never()).saveAll(sets);
  }

  @Test
  void deleteNotFound() {
    final UUID setId = UUID.randomUUID();

    when(setDao.getSetFullById(setId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class, () -> deleteSetService.execute(setId));
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
        Assertions.assertThrows(
            IllegalAccessException.class, () -> deleteSetService.execute(set.getId()));
    assertEquals(Set.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(set.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(deleteOperation, exception.getAuthOperations());
    verify(setDao, never()).deleteById(any());
    verify(entitySorter, never()).sortDelete(any(), any());
    verify(setDao, never()).saveAll(any());
  }
}
