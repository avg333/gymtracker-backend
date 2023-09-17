package org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseUses;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseUsesDao;
import org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.application.model.DecrementExerciseUsesResponseApplication;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class DecrementExerciseUsesServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private DecrementExerciseUsesServiceImpl decrementExerciseUsesService;

  @Mock private AuthExercisesService authExercisesService;
  @Mock private ExerciseDao exerciseDao;
  @Mock private ExerciseUsesDao exerciseUsesDao;

  @Test
  void decrementOk() {
    final int uses = getPositiveRandomInt();
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    final ExerciseUses exerciseUses = easyRandom.nextObject(ExerciseUses.class);
    exerciseUses.setExercise(exercise);
    exerciseUses.setUses(uses);

    when(authExercisesService.getLoggedUserId()).thenReturn(exerciseUses.getUserId());
    when(exerciseDao.findById(exercise.getId())).thenReturn(Optional.of(exercise));
    doNothing().when(authExercisesService).checkAccess(exercise, AuthOperations.READ);
    when(exerciseUsesDao.getExerciseUsesByExerciseIdAndUserId(
            exercise.getId(), exerciseUses.getUserId()))
        .thenReturn(List.of(exerciseUses));
    final ArgumentCaptor<ExerciseUses> exerciseUsesArgumentCaptor =
        ArgumentCaptor.forClass(ExerciseUses.class);
    when(exerciseUsesDao.save(exerciseUsesArgumentCaptor.capture())).thenReturn(null);

    final DecrementExerciseUsesResponseApplication result =
        decrementExerciseUsesService.execute(exercise.getId());
    assertThat(result).isNotNull();
    assertThat(result.getUses()).isEqualTo(uses - 1);

    final ExerciseUses exerciseUsesSaved = exerciseUsesArgumentCaptor.getValue();
    assertThat(exerciseUsesSaved).isNotNull();
    assertThat(exerciseUsesSaved.getUses()).isEqualTo(uses - 1);
    assertThat(exerciseUsesSaved.getExercise()).isEqualTo(exercise);
    assertThat(exerciseUsesSaved.getUserId()).isEqualTo(exerciseUses.getUserId());

    verify(exerciseUsesDao).save(exerciseUsesSaved);
  }

  @Test
  void decrementZeroOk() {
    final int uses = 0;
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    final ExerciseUses exerciseUses = easyRandom.nextObject(ExerciseUses.class);
    exerciseUses.setExercise(exercise);
    exerciseUses.setUses(uses);

    when(authExercisesService.getLoggedUserId()).thenReturn(exerciseUses.getUserId());
    when(exerciseDao.findById(exercise.getId())).thenReturn(Optional.of(exercise));
    doNothing().when(authExercisesService).checkAccess(exercise, AuthOperations.READ);
    when(exerciseUsesDao.getExerciseUsesByExerciseIdAndUserId(
            exercise.getId(), exerciseUses.getUserId()))
        .thenReturn(List.of(exerciseUses));
    final ArgumentCaptor<ExerciseUses> exerciseUsesArgumentCaptor =
        ArgumentCaptor.forClass(ExerciseUses.class);
    when(exerciseUsesDao.save(exerciseUsesArgumentCaptor.capture())).thenReturn(null);

    final DecrementExerciseUsesResponseApplication result =
        decrementExerciseUsesService.execute(exercise.getId());
    assertThat(result).isNotNull();
    assertThat(result.getUses()).isZero();

    final ExerciseUses exerciseUsesSaved = exerciseUsesArgumentCaptor.getValue();
    assertThat(exerciseUsesSaved).isNotNull();
    assertThat(exerciseUsesSaved.getUses()).isZero();
    assertThat(exerciseUsesSaved.getExercise()).isEqualTo(exercise);
    assertThat(exerciseUsesSaved.getUserId()).isEqualTo(exerciseUses.getUserId());

    verify(exerciseUsesDao).save(exerciseUsesSaved);
  }

  @Test
  void decrementFirstTimeOk() {
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    final UUID userId = UUID.randomUUID();

    when(authExercisesService.getLoggedUserId()).thenReturn(userId);
    when(exerciseDao.findById(exercise.getId())).thenReturn(Optional.of(exercise));
    doNothing().when(authExercisesService).checkAccess(exercise, AuthOperations.READ);
    when(exerciseUsesDao.getExerciseUsesByExerciseIdAndUserId(exercise.getId(), userId))
        .thenReturn(Collections.emptyList());
    final ArgumentCaptor<ExerciseUses> exerciseUsesArgumentCaptor =
        ArgumentCaptor.forClass(ExerciseUses.class);
    when(exerciseUsesDao.save(exerciseUsesArgumentCaptor.capture())).thenReturn(null);

    final DecrementExerciseUsesResponseApplication result =
        decrementExerciseUsesService.execute(exercise.getId());
    assertThat(result).isNotNull();
    assertThat(result.getUses()).isZero();

    final ExerciseUses exerciseUsesSaved = exerciseUsesArgumentCaptor.getValue();
    assertThat(exerciseUsesSaved).isNotNull();
    assertThat(exerciseUsesSaved.getUses()).isZero();
    assertThat(exerciseUsesSaved.getExercise()).isEqualTo(exercise);
    assertThat(exerciseUsesSaved.getUserId()).isEqualTo(userId);

    verify(exerciseUsesDao).save(exerciseUsesSaved);
  }

  @Test
  void incrementKoExerciseNotFound() {
    final UUID userId = UUID.randomUUID();
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    final AuthOperations authOperation = AuthOperations.READ;
    final UUID exerciseId = exercise.getId();

    when(authExercisesService.getLoggedUserId()).thenReturn(userId);
    when(exerciseDao.findById(exerciseId)).thenReturn(Optional.of(exercise));
    doThrow(new IllegalAccessException(exercise, authOperation, userId))
        .when(authExercisesService)
        .checkAccess(exercise, authOperation);
    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class, () -> decrementExerciseUsesService.execute(exerciseId));
    assertThat(exception.getEntityClassName()).isEqualTo(exercise.getClass().getSimpleName());
    assertThat(exception.getEntityId()).isEqualTo(exerciseId);
    assertThat(exception.getAuthOperations()).isEqualTo(authOperation);
    assertThat(exception.getCurrentUserId()).isEqualTo(userId);

    verify(exerciseUsesDao, never()).save(any());
  }

  @Test
  void incrementKoExerciseNotAccess() {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();

    when(authExercisesService.getLoggedUserId()).thenReturn(userId);
    when(exerciseDao.findById(exerciseId)).thenReturn(Optional.empty());

    final ExerciseNotFoundException exception =
        assertThrows(
            ExerciseNotFoundException.class,
            () -> decrementExerciseUsesService.execute(exerciseId));
    assertThat(exception.getId()).isEqualTo(exerciseId);
    assertThat(exception.getAccessError())
        .isEqualTo(ExerciseNotFoundException.AccessError.NOT_FOUND);

    verify(exerciseUsesDao, never()).save(any());
  }

  private int getPositiveRandomInt() {
    return Math.max(1, easyRandom.nextInt());
  }
}
