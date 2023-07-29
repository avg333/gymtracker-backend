package org.avillar.gymtracker.workoutapi.set.updatesetdata.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
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
    final UpdateSetDataRequestApplication expected =
        easyRandom.nextObject(UpdateSetDataRequestApplication.class);
    expected.setCompleted(true);
    set.setCompletedAt(null);

    when(setDao.getSetFullById(set.getId())).thenReturn(List.of(set));
    doNothing().when(authWorkoutsService).checkAccess(set, AuthOperations.UPDATE);
    when(setDao.save(any(Set.class))).thenAnswer(i -> i.getArguments()[0]);
    
    final UpdateSetDataResponseApplication result =
        updateSetDataService.execute(set.getId(), expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(set);
    //    assertTrue(
    //        result.getCompletedAt().equals(new Date())
    //            || result.getCompletedAt().after(timestampBeforeCall)); // FIXME

    assertTrue(
        result.getCompletedAt().equals(new Date()) || result.getCompletedAt().before(new Date()));
    verify(setDao).save(set);
  }

  @Test
  void postSameDataWithDate() {
    final Set set = easyRandom.nextObject(Set.class);
    final UpdateSetDataRequestApplication updateSetDataRequestApplication =
        new UpdateSetDataRequestApplication();
    updateSetDataRequestApplication.setDescription(set.getDescription());
    updateSetDataRequestApplication.setWeight(set.getWeight());
    updateSetDataRequestApplication.setRir(set.getRir());
    updateSetDataRequestApplication.setReps(set.getReps());
    updateSetDataRequestApplication.setCompleted(set.getCompletedAt() != null);

    when(setDao.getSetFullById(set.getId())).thenReturn(List.of(set));
    doNothing().when(authWorkoutsService).checkAccess(set, AuthOperations.UPDATE);

    final UpdateSetDataResponseApplication result =
        updateSetDataService.execute(set.getId(), updateSetDataRequestApplication);
    assertThat(result).usingRecursiveComparison().isEqualTo(set);
    verify(setDao, never()).save(any());
  }

  @Test
  void postSameDataWithNoDate() {
    final Set set = easyRandom.nextObject(Set.class);
    set.setCompletedAt(null);
    final UpdateSetDataRequestApplication updateSetDataRequestApplication =
        new UpdateSetDataRequestApplication();
    updateSetDataRequestApplication.setDescription(set.getDescription());
    updateSetDataRequestApplication.setWeight(set.getWeight());
    updateSetDataRequestApplication.setRir(set.getRir());
    updateSetDataRequestApplication.setReps(set.getReps());
    updateSetDataRequestApplication.setCompleted(set.getCompletedAt() != null);

    when(setDao.getSetFullById(set.getId())).thenReturn(List.of(set));
    doNothing().when(authWorkoutsService).checkAccess(set, AuthOperations.UPDATE);

    final UpdateSetDataResponseApplication result =
        updateSetDataService.execute(set.getId(), updateSetDataRequestApplication);
    assertThat(result).usingRecursiveComparison().isEqualTo(set);
    verify(setDao, never()).save(any());
  }

  @Test
  void setNotFound() {
    final UUID setId = UUID.randomUUID();

    when(setDao.getSetFullById(setId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class,
            () ->
                updateSetDataService.execute(
                    setId, easyRandom.nextObject(UpdateSetDataRequestApplication.class)));
    assertEquals(Set.class.getSimpleName(), exception.getClassName());
    assertEquals(setId, exception.getId());
    verify(setDao, never()).save(any());
  }

  @Test
  void updateNotPermission() {
    final UUID userId = UUID.randomUUID();
    final Set set = easyRandom.nextObject(Set.class);
    final AuthOperations deleteOperation = AuthOperations.UPDATE;

    when(setDao.getSetFullById(set.getId())).thenReturn(List.of(set));
    doThrow(new IllegalAccessException(set, deleteOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(set, deleteOperation);

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class,
            () ->
                updateSetDataService.execute(
                    set.getId(), easyRandom.nextObject(UpdateSetDataRequestApplication.class)));
    assertEquals(Set.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(set.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(deleteOperation, exception.getAuthOperations());
    verify(setDao, never()).save(any());
  }
}
