package org.avillar.gymtracker.workoutapi.set.updatesetdata.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.mapper.UpdateSetDataServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataRequestApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponseApplication;
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
class UpdateSetDataServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UpdateSetDataServiceImpl updateSetDataService;

  @Mock private SetDao setDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private UpdateSetDataServiceMapperImpl updateSetDataServiceMapper;

  @Test
  void updateOk() {
    final Set set = easyRandom.nextObject(Set.class);
    final String description = "Description example 54.";
    final double weight = 115.2;
    final double rir = 8.0;
    final int reps = 6;
    final UpdateSetDataRequestApplication updateSetDataRequestApplication =
        new UpdateSetDataRequestApplication();
    updateSetDataRequestApplication.setDescription(description);
    updateSetDataRequestApplication.setWeight(weight);
    updateSetDataRequestApplication.setRir(rir);
    updateSetDataRequestApplication.setReps(reps);

    when(setDao.getSetFullById(set.getId())).thenReturn(List.of(set));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(set, AuthOperations.UPDATE);
    when(setDao.save(any(Set.class))).thenAnswer(i -> i.getArguments()[0]);

    final UpdateSetDataResponseApplication updateSetDataResponseApplication =
        updateSetDataService.execute(set.getId(), updateSetDataRequestApplication);
    Assertions.assertEquals(description, updateSetDataResponseApplication.getDescription());
    Assertions.assertEquals(weight, updateSetDataResponseApplication.getWeight());
    Assertions.assertEquals(rir, updateSetDataResponseApplication.getRir());
    Assertions.assertEquals(reps, updateSetDataResponseApplication.getReps());
    verify(setDao).save(set);
  }

  @Test
  void postSameDescription() {
    final Set set = easyRandom.nextObject(Set.class);
    final UpdateSetDataRequestApplication updateSetDataRequestApplication =
        new UpdateSetDataRequestApplication();
    updateSetDataRequestApplication.setDescription(set.getDescription());
    updateSetDataRequestApplication.setWeight(set.getWeight());
    updateSetDataRequestApplication.setRir(set.getRir());
    updateSetDataRequestApplication.setReps(set.getReps());

    when(setDao.getSetFullById(set.getId())).thenReturn(List.of(set));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(set, AuthOperations.UPDATE);

    final UpdateSetDataResponseApplication updateSetDataResponseApplication =
        updateSetDataService.execute(set.getId(), updateSetDataRequestApplication);
    Assertions.assertEquals(
        set.getDescription(), updateSetDataResponseApplication.getDescription());
    Assertions.assertEquals(set.getWeight(), updateSetDataResponseApplication.getWeight());
    Assertions.assertEquals(set.getRir(), updateSetDataResponseApplication.getRir());
    Assertions.assertEquals(set.getReps(), updateSetDataResponseApplication.getReps());
    verify(setDao, never()).save(any());
  }

  @Test
  void updateNotFound() {
    final UUID setId = UUID.randomUUID();
    final UpdateSetDataRequestApplication updateSetDataRequestApplication =
        easyRandom.nextObject(UpdateSetDataRequestApplication.class);

    when(setDao.getSetFullById(setId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> updateSetDataService.execute(setId, updateSetDataRequestApplication));
    assertEquals(Set.class.getSimpleName(), exception.getClassName());
    assertEquals(setId, exception.getId());
    verify(setDao, never()).save(any());
  }

  @Test
  void updateNotPermission() {
    final UUID userId = UUID.randomUUID();
    final Set set = easyRandom.nextObject(Set.class);
    final UpdateSetDataRequestApplication updateSetDataRequestApplication =
        easyRandom.nextObject(UpdateSetDataRequestApplication.class);
    final AuthOperations authOperation = AuthOperations.UPDATE;

    when(setDao.getSetFullById(set.getId())).thenReturn(List.of(set));
    doThrow(new IllegalAccessException(set, authOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(set, authOperation);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> updateSetDataService.execute(set.getId(), updateSetDataRequestApplication));
    assertEquals(Set.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(set.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperation, exception.getAuthOperations());
  }
}
