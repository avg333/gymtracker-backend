package org.avillar.gymtracker.workoutapi.set.application.get.set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.application.get.set.mapper.GetSetServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.set.application.get.set.model.GetSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.set.domain.SetDao;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetSetServiceImplTest {
  private GetSetService getSetService;

  @Mock private SetDao setDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private GetSetServiceMapperImpl getSetServiceMapper;

  @BeforeEach
  void beforeEach() {
    getSetService = new GetSetServiceImpl(setDao, authWorkoutsService, getSetServiceMapper);
  }

  @Test
  void getOk() {
    final UUID setId = UUID.randomUUID();
    final Set set = new Set();
    final int listOrder = 3;
    final int reps = 6;
    final double rir = 7.6;
    final double weight = 75.0;
    set.setId(setId);
    set.setListOrder(listOrder);
    set.setRir(rir);
    set.setReps(reps);
    set.setWeight(weight);
    set.setSetGroup(new SetGroup());

    when(setDao.getSetFullById(setId)).thenReturn(List.of(set));
    Mockito.doNothing()
        .when(authWorkoutsService)
        .checkAccess(Mockito.any(Set.class), eq(AuthOperations.READ));

    final GetSetResponseApplication getSetResponseApplication = getSetService.getSet(setId);
    Assertions.assertEquals(setId, getSetResponseApplication.getId());
    Assertions.assertEquals(listOrder, getSetResponseApplication.getListOrder());
    assertNull(getSetResponseApplication.getDescription());
    Assertions.assertEquals(rir, getSetResponseApplication.getRir());
    Assertions.assertEquals(reps, getSetResponseApplication.getReps());
    Assertions.assertEquals(weight, getSetResponseApplication.getWeight());
    Assertions.assertInstanceOf(
        GetSetResponseApplication.SetGroup.class, getSetResponseApplication.getSetGroup());
  }

  @Test
  void getNotFound() {
    final UUID setId = UUID.randomUUID();

    when(setDao.getSetFullById(setId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(EntityNotFoundException.class, () -> getSetService.getSet(setId));
    assertEquals(Set.class.getSimpleName(), exception.getClassName());
    assertEquals(setId, exception.getId());
  }

  @Test
  void getNotPermission() {
    final UUID userId = UUID.randomUUID();
    final UUID setId = UUID.randomUUID();
    final Set set = new Set();
    set.setId(setId);

    when(setDao.getSetFullById(setId)).thenReturn(List.of(set));
    doThrow(new IllegalAccessException(set, AuthOperations.READ, userId))
        .when(authWorkoutsService)
        .checkAccess(Mockito.any(Set.class), eq(AuthOperations.READ));

    final IllegalAccessException exception =
        Assertions.assertThrows(IllegalAccessException.class, () -> getSetService.getSet(setId));
    assertEquals(Set.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(setId, exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.READ, exception.getAuthOperations());
  }
}
