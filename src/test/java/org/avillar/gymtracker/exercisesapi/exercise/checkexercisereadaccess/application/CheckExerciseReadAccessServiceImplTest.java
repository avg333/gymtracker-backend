package org.avillar.gymtracker.exercisesapi.exercise.checkexercisereadaccess.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class CheckExerciseReadAccessServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CheckExerciseReadAccessServiceImpl checkExerciseReadAccess;

  @Mock private ExerciseDao exerciseDao;
  @Mock private AuthExercisesService authExercisesService;

  @Test
  void getOk() {
    final Exercise expected = easyRandom.nextObject(Exercise.class);

    when(exerciseDao.findById(expected.getId())).thenReturn(Optional.of(expected));
    doNothing().when(authExercisesService).checkAccess(expected, AuthOperations.READ);

    assertDoesNotThrow(() -> checkExerciseReadAccess.execute(expected.getId()));
  }

  @Test
  void getNotPermission() {
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    final UUID exerciseId = exercise.getId();
    final AuthOperations authOperation = AuthOperations.READ;
    final UUID userId = UUID.randomUUID();
    exercise.setAccessType(AccessTypeEnum.PRIVATE);
    exercise.setOwner(UUID.randomUUID());

    when(exerciseDao.findById(exerciseId)).thenReturn(Optional.of(exercise));
    doThrow(new IllegalAccessException(exercise, authOperation, userId))
        .when(authExercisesService)
        .checkAccess(exercise, authOperation);

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class, () -> checkExerciseReadAccess.execute(exerciseId));
    assertEquals(Exercise.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(exerciseId, exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperation, exception.getAuthOperations());
  }

  @Test
  void getNotFound() {
    final UUID exerciseId = UUID.randomUUID();

    when(exerciseDao.findById(exerciseId))
        .thenThrow(new EntityNotFoundException(Exercise.class, exerciseId));

    final EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class, () -> checkExerciseReadAccess.execute(exerciseId));
    assertEquals(Exercise.class.getSimpleName(), exception.getClassName());
    assertEquals(exerciseId, exception.getId());
  }
}
