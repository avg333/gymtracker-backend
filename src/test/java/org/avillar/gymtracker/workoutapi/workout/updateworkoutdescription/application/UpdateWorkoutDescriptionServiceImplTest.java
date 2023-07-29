package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class UpdateWorkoutDescriptionServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UpdateWorkoutDescriptionServiceImpl updateWorkoutDescriptionService;

  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void postOk() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    final String description = easyRandom.nextObject(String.class);

    when(workoutDao.findById(workout.getId())).thenReturn(Optional.of(workout));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(workout, AuthOperations.UPDATE);
    when(workoutDao.save(workout)).thenAnswer(i -> i.getArguments()[0]);

    final String result = updateWorkoutDescriptionService.execute(workout.getId(), description);
    assertEquals(description, result);
    verify(workoutDao).save(workout);
  }

  @Test
  void postSameDescription() {
    final Workout workout = easyRandom.nextObject(Workout.class);

    when(workoutDao.findById(workout.getId())).thenReturn(Optional.of(workout));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(workout, AuthOperations.UPDATE);

    assertEquals(
        workout.getDescription(),
        updateWorkoutDescriptionService.execute(workout.getId(), workout.getDescription()));
    verify(workoutDao, never()).save(any());
  }

  @Test
  void updateNotFound() {
    final UUID workoutId = UUID.randomUUID();
    final String description = easyRandom.nextObject(String.class);

    when(workoutDao.findById(workoutId))
        .thenThrow(new EntityNotFoundException(Workout.class, workoutId));

    final EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class,
            () -> updateWorkoutDescriptionService.execute(workoutId, description));
    assertEquals(Workout.class.getSimpleName(), exception.getClassName());
    assertEquals(workoutId, exception.getId());
    verify(workoutDao, never()).save(any());
  }

  @Test
  void updateNotPermission() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    final UUID userId = UUID.randomUUID();
    final String description = easyRandom.nextObject(String.class);
    final AuthOperations updateOperation = AuthOperations.UPDATE;

    when(workoutDao.findById(workout.getId())).thenReturn(Optional.of(workout));
    doThrow(new IllegalAccessException(workout, updateOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(workout, updateOperation);

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class,
            () -> updateWorkoutDescriptionService.execute(workout.getId(), description));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workout.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(updateOperation, exception.getAuthOperations());
    verify(workoutDao, never()).save(any());
  }
}
