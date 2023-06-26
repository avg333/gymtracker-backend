package org.avillar.gymtracker.workoutapi.workout.application.get.workoutsimple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.application.get.workoutsimple.mapper.GetWorkoutSimpleServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.application.get.workoutsimple.model.GetWorkoutSimpleResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
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
class GetWorkoutSimpleServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  private GetWorkoutSimpleService getWorkoutSimpleService;

  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private GetWorkoutSimpleServiceMapperImpl getWorkoutSimpleServiceMapper;

  @BeforeEach
  void beforeEach() {
    getWorkoutSimpleService =
        new GetWorkoutSimpleServiceImpl(
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

    final GetWorkoutSimpleResponseApplication getWorkoutSimpleResponseApplication =
        getWorkoutSimpleService.execute(workout.getId());
    Assertions.assertEquals(workout.getId(), getWorkoutSimpleResponseApplication.getId());
    Assertions.assertEquals(workout.getDate(), getWorkoutSimpleResponseApplication.getDate());
    Assertions.assertEquals(
        workout.getDescription(), getWorkoutSimpleResponseApplication.getDescription());
    Assertions.assertEquals(workout.getUserId(), getWorkoutSimpleResponseApplication.getUserId());
  }

  @Test
  void getNotFound() {
    final UUID workoutId = UUID.randomUUID();

    when(workoutDao.findById(workoutId))
        .thenThrow(new EntityNotFoundException(Workout.class, workoutId));

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class, () -> getWorkoutSimpleService.execute(workoutId));
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
            IllegalAccessException.class, () -> getWorkoutSimpleService.execute(workoutId));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workoutId, exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.READ, exception.getAuthOperations());
  }
}
