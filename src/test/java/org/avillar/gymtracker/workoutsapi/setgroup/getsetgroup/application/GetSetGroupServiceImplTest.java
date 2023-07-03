package org.avillar.gymtracker.workoutsapi.setgroup.getsetgroup.application;

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
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.GetSetGroupServiceImpl;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.mapper.GetSetGroupServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.model.GetSetGroupResponseApplication;
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
class GetSetGroupServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetSetGroupServiceImpl getSetGroupService;

  @Mock private SetGroupDao setGroupDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private GetSetGroupServiceMapperImpl getSetGroupServiceMapper;

  @Test
  void getOk() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);

    when(setGroupDao.getSetGroupWithWorkoutById(setGroup.getId())).thenReturn(List.of(setGroup));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.READ);

    final GetSetGroupResponseApplication result = getSetGroupService.execute(setGroup.getId());
    assertEquals(setGroup.getId(), result.getId());
    assertEquals(setGroup.getListOrder(), result.getListOrder());
    assertEquals(setGroup.getDescription(), result.getDescription());
    assertEquals(setGroup.getExerciseId(), result.getExerciseId());
    assertEquals(setGroup.getWorkout().getId(), result.getWorkout().getId());
  }

  @Test
  void getNotFound() {
    final UUID setGroupId = UUID.randomUUID();

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class, () -> getSetGroupService.execute(setGroupId));
    assertEquals(SetGroup.class.getSimpleName(), exception.getClassName());
    assertEquals(setGroupId, exception.getId());
  }

  @Test
  void getNotPermission() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final UUID userId = UUID.randomUUID();
    final AuthOperations authOperation = AuthOperations.READ;

    when(setGroupDao.getSetGroupWithWorkoutById(setGroup.getId())).thenReturn(List.of(setGroup));
    doThrow(new IllegalAccessException(setGroup, authOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(setGroup, authOperation);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class, () -> getSetGroupService.execute(setGroup.getId()));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(setGroup.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperation, exception.getAuthOperations());
  }
}
