package org.avillar.gymtracker.workoutapi.setgroup.application.update.exercise;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.domain.Exercise;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.exercise.application.facade.ExerciseRepositoryClient;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateSetGroupExerciseServiceImplTest {

  @InjectMocks UpdateSetGroupExerciseServiceImpl updateSetGroupExerciseService;

  @Mock private SetGroupDao setGroupDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Mock private ExerciseRepositoryClient exerciseRepositoryClient;

  @Test
  void updateOk() {
    final UUID setGroupId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();

    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    setGroup.setExerciseId(UUID.randomUUID());
    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(List.of(setGroup));
    when(exerciseRepositoryClient.canAccessExerciseById(exerciseId)).thenReturn(true);
    Mockito.doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.UPDATE);
    when(setGroupDao.save(Mockito.any(SetGroup.class))).thenAnswer(i -> i.getArguments()[0]);

    Assertions.assertEquals(
        exerciseId, updateSetGroupExerciseService.update(setGroupId, exerciseId));
  }

  @Test
  void updateSameExerciseId() {
    final UUID setGroupId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();

    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    setGroup.setExerciseId(exerciseId);

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(List.of(setGroup));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.UPDATE);

    verify(setGroupDao, never()).save(Mockito.any(SetGroup.class));
    Assertions.assertEquals(
        exerciseId, updateSetGroupExerciseService.update(setGroupId, exerciseId));
  }

  @Test
  void exerciseNotFound() {
    final UUID setGroupId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();

    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    setGroup.setExerciseId(UUID.randomUUID());

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(List.of(setGroup));
    when(exerciseRepositoryClient.canAccessExerciseById(exerciseId)).thenReturn(false);

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> updateSetGroupExerciseService.update(setGroupId, exerciseId));
    assertEquals(Exercise.class.getSimpleName(), exception.getClassName());
    assertEquals(exerciseId, exception.getId());
  }

  @Test
  void updateNotFound() {
    final UUID setGroupId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();

    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    setGroup.setExerciseId(UUID.randomUUID());
    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> updateSetGroupExerciseService.update(setGroupId, exerciseId));
    assertEquals(SetGroup.class.getSimpleName(), exception.getClassName());
    assertEquals(setGroupId, exception.getId());
  }

  @Test
  void updateNotPermission() {
    final UUID userId = UUID.randomUUID();
    final UUID setGroupId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();

    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    setGroup.setExerciseId(UUID.randomUUID());

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(List.of(setGroup));
    doThrow(new IllegalAccessException(setGroup, AuthOperations.UPDATE, userId))
        .when(authWorkoutsService)
        .checkAccess(setGroup, AuthOperations.UPDATE);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> updateSetGroupExerciseService.update(setGroupId, exerciseId));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(setGroupId, exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.UPDATE, exception.getAuthOperations());
  }
}
