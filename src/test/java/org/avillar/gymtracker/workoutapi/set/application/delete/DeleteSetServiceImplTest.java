package org.avillar.gymtracker.workoutapi.set.application.delete;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteSetServiceImplTest {

  private final UUID setId = UUID.randomUUID();
  private final UUID setGroupId = UUID.randomUUID();

  @InjectMocks private DeleteSetServiceImpl deleteSetService;

  @Mock private SetDao setDao;

  @Mock private AuthWorkoutsService authWorkoutsService;

  @Mock private EntitySorter entitySorter;

  @Test
  void deleteOk() {
    final Set set = new Set();
    set.setId(setId);
    set.setListOrder(0);
    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    set.setSetGroup(setGroup);

    when(setDao.getSetFullById(setId)).thenReturn(List.of(set));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(set, AuthOperations.DELETE);
    when(setDao.getSetsBySetGroupId(setGroupId)).thenReturn(java.util.Set.of(set));

    Assertions.assertDoesNotThrow(() -> deleteSetService.execute(setId));
    // TODO Revisar caso de lista vacia y no vacia
  }

  //  @Test
  //  void updateSameValue() {
  //    final Set set = new Set();
  //    set.setId(setId);
  //    set.setListOrder(0);
  //    final SetGroup setGroup = new SetGroup();
  //    setGroup.setId(setGroupId);
  //    set.setSetGroup(setGroup);
  //
  //    when(setDao.getSetFullById(setId)).thenReturn(List.of(set));
  //    Mockito.doNothing().when(authWorkoutsService).checkAccess(set, AuthOperations.DELETE);
  //    when(setDao.getSetsBySetGroupId(setGroupId)).thenReturn(java.util.Set.of(set));
  //    verify(entitySorter, never()).sortUpdate(java.util.Set.of(set), set, set.getListOrder());
  //  } FIXME

  @Test
  void deleteNotFound() {
    when(setDao.getSetFullById(setId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class, () -> deleteSetService.execute(setId));
    assertEquals(Set.class.getSimpleName(), exception.getClassName());
    assertEquals(setId, exception.getId());
  }

  @Test
  void deleteNotPermission() {
    final Set set = new Set();
    set.setId(setId);
    final UUID userId = UUID.randomUUID();

    when(setDao.getSetFullById(setId)).thenReturn(List.of(set));
    doThrow(new IllegalAccessException(set, AuthOperations.DELETE, userId))
        .when(authWorkoutsService)
        .checkAccess(set, AuthOperations.DELETE);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class, () -> deleteSetService.execute(setId));
    assertEquals(Set.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(setId, exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.DELETE, exception.getAuthOperations());
  }
}
