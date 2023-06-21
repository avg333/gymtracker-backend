package org.avillar.gymtracker.workoutapi.set.application.update.data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.application.update.data.mapper.UpdateSetDataServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.set.application.update.data.model.UpdateSetDataRequestApplication;
import org.avillar.gymtracker.workoutapi.set.application.update.data.model.UpdateSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.set.domain.SetDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateSetDataServiceImplTest {

  private UpdateSetDataService updateSetDataService;

  @Mock private SetDao setDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private UpdateSetDataServiceMapperImpl updateSetDataServiceMapper;

  @BeforeEach
  void beforeEach() {
    updateSetDataService =
        new UpdateSetDataServiceImpl(setDao, authWorkoutsService, updateSetDataServiceMapper);
  }

  @Test
  void updateOk() {
    final UUID setId = UUID.randomUUID();
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

    final Set set = new Set();
    set.setId(setId);
    set.setReps(5);
    set.setRir(8.0);
    set.setWeight(15.2);
    when(setDao.getSetFullById(setId)).thenReturn(List.of(set));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(set, AuthOperations.UPDATE);
    when(setDao.save(Mockito.any(Set.class))).thenAnswer(i -> i.getArguments()[0]);

    final UpdateSetDataResponseApplication updateSetDataResponseApplication =
        updateSetDataService.update(setId, updateSetDataRequestApplication);
    Assertions.assertEquals(description, updateSetDataResponseApplication.getDescription());
    Assertions.assertEquals(weight, updateSetDataResponseApplication.getWeight());
    Assertions.assertEquals(rir, updateSetDataResponseApplication.getRir());
    Assertions.assertEquals(reps, updateSetDataResponseApplication.getReps());
  }

  @Test
  void postSameDescription() {
    final UUID setId = UUID.randomUUID();
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

    final Set set = new Set();
    set.setId(setId);
    set.setDescription(description);
    set.setReps(reps);
    set.setRir(rir);
    set.setWeight(weight);
    when(setDao.getSetFullById(setId)).thenReturn(List.of(set));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(set, AuthOperations.UPDATE);
    verify(setDao, never()).save(Mockito.any(Set.class));

    final UpdateSetDataResponseApplication updateSetDataResponseApplication =
        updateSetDataService.update(setId, updateSetDataRequestApplication);
    Assertions.assertEquals(description, updateSetDataResponseApplication.getDescription());
    Assertions.assertEquals(weight, updateSetDataResponseApplication.getWeight());
    Assertions.assertEquals(rir, updateSetDataResponseApplication.getRir());
    Assertions.assertEquals(reps, updateSetDataResponseApplication.getReps());
  }

  @Test
  void updateNotFound() {
    final UUID setId = UUID.randomUUID();
    final UpdateSetDataRequestApplication updateSetDataRequestApplication =
        new UpdateSetDataRequestApplication();

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> updateSetDataService.update(setId, updateSetDataRequestApplication));
    assertEquals(Set.class.getSimpleName(), exception.getClassName());
    assertEquals(setId, exception.getId());
  }

  @Test
  void updateNotPermission() {
    final UUID userId = UUID.randomUUID();
    final UUID setId = UUID.randomUUID();
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

    final Set set = new Set();
    set.setId(setId);
    set.setReps(5);
    set.setRir(8.0);
    set.setWeight(15.2);
    when(setDao.getSetFullById(setId)).thenReturn(List.of(set));
    doThrow(new IllegalAccessException(set, AuthOperations.UPDATE, userId))
        .when(authWorkoutsService)
        .checkAccess(set, AuthOperations.UPDATE);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> updateSetDataService.update(setId, updateSetDataRequestApplication));
    assertEquals(Set.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(setId, exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.UPDATE, exception.getAuthOperations());
  }
}
