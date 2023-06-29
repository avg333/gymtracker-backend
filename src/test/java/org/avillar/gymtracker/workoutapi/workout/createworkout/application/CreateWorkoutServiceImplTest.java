package org.avillar.gymtracker.workoutapi.workout.createworkout.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.mapper.CreateWorkoutServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutRequestApplication;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateWorkoutServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CreateWorkoutServiceImpl createWorkoutService;

  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private CreateWorkoutServiceMapperImpl createWorkoutServiceMapper;

  @Test
  void createOk() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    final CreateWorkoutRequestApplication createWorkoutRequestApplication =
        new CreateWorkoutRequestApplication();
    createWorkoutRequestApplication.setDate(workout.getDate());
    createWorkoutRequestApplication.setDescription(workout.getDescription());

    when(workoutDao.existsWorkoutByUserAndDate(workout.getUserId(), workout.getDate()))
        .thenReturn(false);
    doNothing()
        .when(authWorkoutsService)
        .checkAccess(any(Workout.class), eq(AuthOperations.CREATE));
    when(workoutDao.save(any(Workout.class))).thenAnswer(i -> i.getArguments()[0]);
    // TODO Answer workout

    final CreateWorkoutResponseApplication createWorkoutResponseApplication =
        createWorkoutService.execute(workout.getUserId(), createWorkoutRequestApplication);
    assertNotNull(workout.getId());
    assertEquals(workout.getUserId(), createWorkoutResponseApplication.getUserId());
    assertEquals(workout.getDate(), createWorkoutResponseApplication.getDate());
    assertEquals(workout.getDescription(), createWorkoutResponseApplication.getDescription());
    verify(workoutDao).save(any(Workout.class));
  }

  @Test
  void createExistsWorkoutForTheUserInThatDate() {
    final UUID userId = UUID.randomUUID();
    final CreateWorkoutRequestApplication createWorkoutRequestApplication =
        easyRandom.nextObject(CreateWorkoutRequestApplication.class);

    when(workoutDao.existsWorkoutByUserAndDate(userId, createWorkoutRequestApplication.getDate()))
        .thenReturn(true);

    final DuplicatedWorkoutDateException exception =
        Assertions.assertThrows(
            DuplicatedWorkoutDateException.class,
            () -> createWorkoutService.execute(userId, createWorkoutRequestApplication));
    assertEquals(createWorkoutRequestApplication.getDate(), exception.getWorkoutDate());
    assertEquals(userId, exception.getUserId());
    verify(workoutDao, never()).save(any(Workout.class));
  }

  @Test
  void createNotPermission() {
    final UUID userId = UUID.randomUUID();
    final CreateWorkoutRequestApplication createWorkoutRequestApplication =
        easyRandom.nextObject(CreateWorkoutRequestApplication.class);
    final AuthOperations authOperation = AuthOperations.CREATE;

    final Workout workout = createWorkoutServiceMapper.map(createWorkoutRequestApplication);
    doThrow(new IllegalAccessException(workout, authOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(any(Workout.class), eq(authOperation));

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> createWorkoutService.execute(userId, createWorkoutRequestApplication));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertNull(exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperation, exception.getAuthOperations());
    verify(workoutDao, never()).save(any(Workout.class));
  }
}