package org.avillar.gymtracker.workoutsapi.set.createset.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.set.createset.application.CreateSetServiceImpl;
import org.avillar.gymtracker.workoutapi.set.createset.application.mapper.CreateSetServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetResponseApplication;
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
class CreateSetServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CreateSetServiceImpl createSetService;

  @Mock private SetDao setDao;
  @Mock private SetGroupDao setGroupDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private CreateSetServiceMapperImpl createSetServiceMapper;

  @Test
  void postOk() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final Set set = easyRandom.nextObject(Set.class);
    final CreateSetRequestApplication createSetRequestApplication =
        new CreateSetRequestApplication();
    createSetRequestApplication.setDescription(set.getDescription());
    createSetRequestApplication.setWeight(set.getWeight());
    createSetRequestApplication.setRir(set.getRir());
    createSetRequestApplication.setReps(set.getReps());

    when(setGroupDao.getSetGroupFullByIds(List.of(setGroup.getId()))).thenReturn(List.of(setGroup));
    doNothing().when(authWorkoutsService).checkAccess(any(Set.class), eq(AuthOperations.CREATE));
    when(setDao.save(any(Set.class))).thenAnswer(i -> i.getArguments()[0]);

    final CreateSetResponseApplication createSetResponseApplication =
        createSetService.execute(setGroup.getId(), createSetRequestApplication);
    // assertNotNull(result.getId()); TODO Arreglar .save
    assertEquals(setGroup.getId(), createSetResponseApplication.getSetGroup().getId());
    assertEquals(set.getDescription(), createSetResponseApplication.getDescription());
    assertEquals(set.getRir(), createSetResponseApplication.getRir());
    assertEquals(set.getReps(), createSetResponseApplication.getReps());
    assertEquals(set.getWeight(), createSetResponseApplication.getWeight());
    verify(setDao).save(any(Set.class));
  }

  @Test
  void setGroupNotFound() {
    final UUID setGroupId = UUID.randomUUID();
    final CreateSetRequestApplication createSetRequestApplication =
        easyRandom.nextObject(CreateSetRequestApplication.class);

    when(setGroupDao.getSetGroupFullByIds(List.of(setGroupId))).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> createSetService.execute(setGroupId, createSetRequestApplication));
    assertEquals(SetGroup.class.getSimpleName(), exception.getClassName());
    assertEquals(setGroupId, exception.getId());
    verify(setDao, never()).save(any(Set.class));
  }

  @Test
  void deleteNotPermission() {
    final UUID userId = UUID.randomUUID();
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final CreateSetRequestApplication createSetRequestApplication =
        easyRandom.nextObject(CreateSetRequestApplication.class);
    final AuthOperations authOperation = AuthOperations.CREATE;

    when(setGroupDao.getSetGroupFullByIds(List.of(setGroup.getId()))).thenReturn(List.of(setGroup));
    doThrow(new IllegalAccessException(new Set(), authOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(Mockito.any(Set.class), eq(authOperation));

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> createSetService.execute(setGroup.getId(), createSetRequestApplication));
    assertEquals(Set.class.getSimpleName(), exception.getEntityClassName());
    assertNull(exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperation, exception.getAuthOperations());
    verify(setDao, never()).save(any(Set.class));
  }
}