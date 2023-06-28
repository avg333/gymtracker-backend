package org.avillar.gymtracker.workoutapi.workout.getworkout.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.mapper.GetWorkoutServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.model.GetWorkoutResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetWorkoutServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  private GetWorkoutService getWorkoutService;

  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private GetWorkoutServiceMapperImpl getWorkoutSimpleServiceMapper;

  @BeforeEach
  void beforeEach() {
    getWorkoutService =
        new GetWorkoutServiceImpl(
            workoutDao, authWorkoutsService, getWorkoutSimpleServiceMapper);
  }

  @Test
  void getOk() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    workout.setId(UUID.randomUUID());

    when(workoutDao.findById(workout.getId())).thenReturn(Optional.of(workout));
    Mockito.doNothing()
        .when(authWorkoutsService)
        .checkAccess(Mockito.any(Workout.class), eq(AuthOperations.READ));

    final GetWorkoutResponseApplication getWorkoutResponseApplication =
        getWorkoutService.execute(workout.getId());
    Assertions.assertEquals(workout.getId(), getWorkoutResponseApplication.getId());
    Assertions.assertEquals(workout.getDate(), getWorkoutResponseApplication.getDate());
    Assertions.assertEquals(
        workout.getDescription(), getWorkoutResponseApplication.getDescription());
    Assertions.assertEquals(workout.getUserId(), getWorkoutResponseApplication.getUserId());
  }

  @Test
  void getNotFound() {
    final UUID workoutId = UUID.randomUUID();

    when(workoutDao.findById(workoutId))
        .thenThrow(new EntityNotFoundException(Workout.class, workoutId));

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class, () -> getWorkoutService.execute(workoutId));
    assertEquals(Workout.class.getSimpleName(), exception.getClassName());
    assertEquals(workoutId, exception.getId());
  }

  @Test
  void getNotPermission() {
    final UUID workoutId = UUID.randomUUID();
    final Workout workout = new Workout();
    final UUID userId = UUID.randomUUID();
    workout.setId(workoutId);
    workout.setUserId(userId);

    when(workoutDao.findById(workoutId)).thenReturn(Optional.of(workout));
    doThrow(new IllegalAccessException(workout, AuthOperations.READ, userId))
        .when(authWorkoutsService)
        .checkAccess(workout, AuthOperations.READ);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class, () -> getWorkoutService.execute(workoutId));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workoutId, exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.READ, exception.getAuthOperations());
  }
}
