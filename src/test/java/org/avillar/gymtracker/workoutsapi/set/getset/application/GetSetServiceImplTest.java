package org.avillar.gymtracker.workoutsapi.set.getset.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.set.getset.application.GetSetServiceImpl;
import org.avillar.gymtracker.workoutapi.set.getset.application.mapper.GetSetServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.set.getset.application.model.GetSetResponseApplication;
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
class GetSetServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();
  @InjectMocks private GetSetServiceImpl getSetService;

  @Mock private SetDao setDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private GetSetServiceMapperImpl getSetServiceMapper;

  @Test
  void getOk() {
    final Set set = easyRandom.nextObject(Set.class);

    when(setDao.getSetFullById(set.getId())).thenReturn(List.of(set));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(set, AuthOperations.READ);

    final GetSetResponseApplication result = getSetService.execute(set.getId());
    Assertions.assertEquals(set.getId(), result.getId());
    Assertions.assertEquals(set.getListOrder(), result.getListOrder());
    Assertions.assertEquals(set.getRir(), result.getRir());
    Assertions.assertEquals(set.getRir(), result.getRir());
    Assertions.assertEquals(set.getReps(), result.getReps());
    Assertions.assertEquals(set.getWeight(), result.getWeight());
    Assertions.assertEquals(set.getSetGroup().getId(), result.getSetGroup().getId());
  }

  @Test
  void getNotFound() {
    final UUID setId = UUID.randomUUID();

    when(setDao.getSetFullById(setId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(EntityNotFoundException.class, () -> getSetService.execute(setId));
    assertEquals(Set.class.getSimpleName(), exception.getClassName());
    assertEquals(setId, exception.getId());
  }

  @Test
  void getNotPermission() {
    final UUID userId = UUID.randomUUID();
    final Set set = easyRandom.nextObject(Set.class);
    final AuthOperations authOperation = AuthOperations.READ;

    when(setDao.getSetFullById(set.getId())).thenReturn(List.of(set));
    doThrow(new IllegalAccessException(set, authOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(set, authOperation);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class, () -> getSetService.execute(set.getId()));
    assertEquals(Set.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(set.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperation, exception.getAuthOperations());
  }
}
