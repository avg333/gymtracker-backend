package org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.avillar.gymtracker.common.base.domain.BaseEntity;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroupExercise;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroupExerciseDao;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteExerciseServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private DeleteExerciseServiceImpl deleteExerciseService;

  @Mock private ExerciseDao exerciseDao;
  @Mock private MuscleGroupExerciseDao muscleGroupExerciseDao;
  @Mock private AuthExercisesService authExercisesService;

  @Test
  void deleteOk() {
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    exercise.setMuscleGroupExercises(
        easyRandom.objects(MuscleGroupExercise.class, 5).collect(Collectors.toSet()));

    when(exerciseDao.getExerciseByIdWithMuscleGroupEx(exercise.getId()))
        .thenReturn(List.of(exercise));
    doNothing().when(authExercisesService).checkAccess(exercise, AuthOperations.DELETE);

    assertDoesNotThrow(() -> deleteExerciseService.execute(exercise.getId()));
    verify(muscleGroupExerciseDao)
        .deleteAllById(
            exercise.getMuscleGroupExercises().stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList()));
    verify(exerciseDao).deleteById(exercise.getId());
  }

  @Test
  void deleteOkNoMGE() {
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    exercise.setMuscleGroupExercises(new HashSet<>());

    when(exerciseDao.getExerciseByIdWithMuscleGroupEx(exercise.getId()))
        .thenReturn(List.of(exercise));
    doNothing().when(authExercisesService).checkAccess(exercise, AuthOperations.DELETE);

    assertDoesNotThrow(() -> deleteExerciseService.execute(exercise.getId()));
    verify(muscleGroupExerciseDao, never()).deleteAllById(any());
    verify(exerciseDao).deleteById(exercise.getId());
  }

  @Test
  void workoutNotFound() {
    final UUID exerciseId = UUID.randomUUID();

    when(exerciseDao.getExerciseByIdWithMuscleGroupEx(exerciseId))
        .thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class, () -> deleteExerciseService.execute(exerciseId));
    assertEquals(Exercise.class.getSimpleName(), exception.getClassName());
    assertEquals(exerciseId, exception.getId());
    verify(muscleGroupExerciseDao, never()).deleteAllById(any());
    verify(exerciseDao, never()).deleteById(any());
  }

  @Test
  void deleteNotPermission() {
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    exercise.setMuscleGroupExercises(
        easyRandom.objects(MuscleGroupExercise.class, 5).collect(Collectors.toSet()));
    final UUID exerciseId = exercise.getId();
    final UUID userId = UUID.randomUUID();
    final AuthOperations deleteOperation = AuthOperations.DELETE;

    when(exerciseDao.getExerciseByIdWithMuscleGroupEx(exerciseId)).thenReturn(List.of(exercise));
    doThrow(new IllegalAccessException(exercise, deleteOperation, userId))
        .when(authExercisesService)
        .checkAccess(exercise, deleteOperation);

    final IllegalAccessException exception =
        assertThrows(IllegalAccessException.class, () -> deleteExerciseService.execute(exerciseId));
    assertEquals(Exercise.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(exerciseId, exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(deleteOperation, exception.getAuthOperations());
    verify(muscleGroupExerciseDao, never()).deleteAllById(any());
    verify(exerciseDao, never()).deleteById(any());
  }
}
