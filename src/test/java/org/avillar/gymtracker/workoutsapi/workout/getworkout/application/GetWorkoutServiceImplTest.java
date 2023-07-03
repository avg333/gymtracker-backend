package org.avillar.gymtracker.workoutsapi.workout.getworkout.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.GetWorkoutServiceImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.mapper.GetWorkoutServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.model.GetWorkoutResponseApplication;
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
class GetWorkoutServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetWorkoutServiceImpl getWorkoutService;

  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private GetWorkoutServiceMapperImpl getWorkoutServiceMapper;

  @Test
  void getOk() {
    final Workout workout = easyRandom.nextObject(Workout.class);

    when(workoutDao.findById(workout.getId())).thenReturn(Optional.of(workout));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(workout, AuthOperations.READ);

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
    final Workout workout = easyRandom.nextObject(Workout.class);
    final UUID userId = UUID.randomUUID();
    final AuthOperations authOperation = AuthOperations.READ;

    when(workoutDao.findById(workout.getId())).thenReturn(Optional.of(workout));
    doThrow(new IllegalAccessException(workout, authOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(workout, authOperation);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class, () -> getWorkoutService.execute(workout.getId()));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workout.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperation, exception.getAuthOperations());
  }
}
