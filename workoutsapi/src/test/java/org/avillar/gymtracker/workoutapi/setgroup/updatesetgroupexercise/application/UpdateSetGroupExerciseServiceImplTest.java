package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.application;

import static org.junit.jupiter.api.Assertions.*;
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
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.exercise.application.facade.ExerciseRepositoryClient;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateSetGroupExerciseServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UpdateSetGroupExerciseServiceImpl updateSetGroupExerciseService;

  @Mock private SetGroupDao setGroupDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Mock private ExerciseRepositoryClient exerciseRepositoryClient;

  @Test
  void updateOk() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final UUID exerciseId = UUID.randomUUID();

    when(setGroupDao.getSetGroupWithWorkoutById(setGroup.getId())).thenReturn(List.of(setGroup));
    when(exerciseRepositoryClient.canAccessExerciseById(exerciseId)).thenReturn(true);
    Mockito.doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.UPDATE);
    when(setGroupDao.save(Mockito.any(SetGroup.class))).thenAnswer(i -> i.getArguments()[0]);

    Assertions.assertEquals(
        exerciseId, updateSetGroupExerciseService.execute(setGroup.getId(), exerciseId));
    verify(setGroupDao).save(setGroup);
  }

  @Test
  void updateSameExerciseId() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);

    when(setGroupDao.getSetGroupWithWorkoutById(setGroup.getId())).thenReturn(List.of(setGroup));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.UPDATE);

    Assertions.assertEquals(
        setGroup.getExerciseId(),
        updateSetGroupExerciseService.execute(setGroup.getId(), setGroup.getExerciseId()));
    verify(setGroupDao, never()).save(Mockito.any(SetGroup.class));
  }

  @Test
  void exerciseNotFound() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final UUID exerciseId = UUID.randomUUID();

    when(setGroupDao.getSetGroupWithWorkoutById(setGroup.getId())).thenReturn(List.of(setGroup));
    when(exerciseRepositoryClient.canAccessExerciseById(exerciseId)).thenReturn(false);

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> updateSetGroupExerciseService.execute(setGroup.getId(), exerciseId));
    assertEquals(Exercise.class.getSimpleName(), exception.getClassName());
    assertEquals(exerciseId, exception.getId());
    verify(setGroupDao, never()).save(Mockito.any(SetGroup.class));
  }

  @Test
  void updateNotFound() {
    final UUID setGroupId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> updateSetGroupExerciseService.execute(setGroupId, exerciseId));
    assertEquals(SetGroup.class.getSimpleName(), exception.getClassName());
    assertEquals(setGroupId, exception.getId());
    verify(setGroupDao, never()).save(Mockito.any(SetGroup.class));
  }

  @Test
  void updateNotPermission() {
    final UUID userId = UUID.randomUUID();
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final UUID exerciseId = UUID.randomUUID();
    final AuthOperations authOperation = AuthOperations.UPDATE;

    when(setGroupDao.getSetGroupWithWorkoutById(setGroup.getId())).thenReturn(List.of(setGroup));
    doThrow(new IllegalAccessException(setGroup, authOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(setGroup, authOperation);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> updateSetGroupExerciseService.execute(setGroup.getId(), exerciseId));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(setGroup.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperation, exception.getAuthOperations());
    verify(setGroupDao, never()).save(Mockito.any(SetGroup.class));
  }
}
