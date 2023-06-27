package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.application;

import static org.junit.jupiter.api.Assertions.*;
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
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateWorkoutDescriptionServiceImplTest {

  private UpdateWorkoutDescriptionServiceImpl updateWorkoutDescriptionService;

  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @BeforeEach
  void beforeEach() {
    updateWorkoutDescriptionService =
        new UpdateWorkoutDescriptionServiceImpl(workoutDao, authWorkoutsService);
  }

  @Test
  void postOk() {
    final UUID workoutId = UUID.randomUUID();
    final String description = "Description example 54.";
    final Workout workout = new Workout();
    workout.setDescription("Description example 52.");
    workout.setId(workoutId);

    when(workoutDao.findById(workoutId)).thenReturn(Optional.of(workout));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(workout, AuthOperations.UPDATE);
    when(workoutDao.save(Mockito.any(Workout.class))).thenAnswer(i -> i.getArguments()[0]);

    assertEquals(description, updateWorkoutDescriptionService.execute(workoutId, description));
  }

  @Test
  void postSameDescription() {
    final UUID workoutId = UUID.randomUUID();
    final String description = "Description example 54.";
    final Workout workout = new Workout();
    workout.setDescription(description);
    workout.setId(workoutId);

    when(workoutDao.findById(workoutId)).thenReturn(Optional.of(workout));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(workout, AuthOperations.UPDATE);

    verify(workoutDao, never()).save(Mockito.any(Workout.class));
    assertEquals(description, updateWorkoutDescriptionService.execute(workoutId, description));
  }

  @Test
  void updateNotFound() {
    final UUID workoutId = UUID.randomUUID();
    final String description = "Description example 54.";
    final Workout workout = new Workout();
    workout.setDescription("Description example 52.");
    workout.setId(workoutId);

    when(workoutDao.findById(workoutId))
        .thenThrow(new EntityNotFoundException(Workout.class, workoutId));

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> updateWorkoutDescriptionService.execute(workoutId, description));
    assertEquals(Workout.class.getSimpleName(), exception.getClassName());
    assertEquals(workoutId, exception.getId());
  }

  @Test
  void updateNotPermission() {
    final UUID userId = UUID.randomUUID();
    final UUID workoutId = UUID.randomUUID();
    final String description = "Description example 54.";
    final Workout workout = new Workout();
    workout.setDescription("Description example 52.");
    workout.setId(workoutId);

    when(workoutDao.findById(workoutId)).thenReturn(Optional.of(workout));
    doThrow(new IllegalAccessException(workout, AuthOperations.UPDATE, userId))
        .when(authWorkoutsService)
        .checkAccess(workout, AuthOperations.UPDATE);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> updateWorkoutDescriptionService.execute(workoutId, description));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workoutId, exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.UPDATE, exception.getAuthOperations());
  }
}
