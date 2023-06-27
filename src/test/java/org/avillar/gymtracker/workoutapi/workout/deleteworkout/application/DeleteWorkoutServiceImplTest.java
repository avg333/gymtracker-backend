package org.avillar.gymtracker.workoutapi.workout.deleteworkout.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteWorkoutServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();
  @InjectMocks private DeleteWorkoutServiceImpl deleteWorkoutService;
  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void deleteOk() {
    final Workout workout = easyRandom.nextObject(Workout.class);

    when(workoutDao.findById(workout.getId())).thenReturn(Optional.of(workout));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(workout, AuthOperations.DELETE);

    assertDoesNotThrow(() -> deleteWorkoutService.execute(workout.getId()));
  }

  @Test
  void deleteNotFound() {
    final UUID workoutId = UUID.randomUUID();

    when(workoutDao.findById(workoutId))
        .thenThrow(new EntityNotFoundException(Workout.class, workoutId));

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class, () -> deleteWorkoutService.execute(workoutId));
    assertEquals(Workout.class.getSimpleName(), exception.getClassName());
    assertEquals(workoutId, exception.getId());
  }

  @Test
  void deleteNotPermission() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    final UUID userId = UUID.randomUUID();
    final AuthOperations deleteOperation = AuthOperations.DELETE;

    when(workoutDao.findById(workout.getId())).thenReturn(Optional.of(workout));
    doThrow(new IllegalAccessException(workout, deleteOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(workout, deleteOperation);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class, () -> deleteWorkoutService.execute(workout.getId()));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workout.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(deleteOperation, exception.getAuthOperations());
  }
}
