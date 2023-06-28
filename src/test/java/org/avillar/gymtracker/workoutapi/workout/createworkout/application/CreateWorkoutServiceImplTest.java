package org.avillar.gymtracker.workoutapi.workout.createworkout.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Date;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateWorkoutServiceImplTest {
  private final EasyRandom easyRandom = new EasyRandom();

  private CreateWorkoutServiceImpl createWorkoutService;

  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private CreateWorkoutServiceMapperImpl postWorkoutServiceMapper;

  @BeforeEach
  void beforeEach() {
    createWorkoutService =
        new CreateWorkoutServiceImpl(workoutDao, authWorkoutsService, postWorkoutServiceMapper);
  }

  @Test
  void createOk() {
    final UUID userId = UUID.randomUUID();
    final CreateWorkoutRequestApplication createWorkoutRequestApplication =
        easyRandom.nextObject(CreateWorkoutRequestApplication.class);

    when(workoutDao.existsWorkoutByUserAndDate(userId, createWorkoutRequestApplication.getDate()))
        .thenReturn(false);
    doNothing()
        .when(authWorkoutsService)
        .checkAccess(any(Workout.class), eq(AuthOperations.CREATE));
    when(workoutDao.save(any(Workout.class))).thenAnswer(i -> i.getArguments()[0]);

    final CreateWorkoutResponseApplication createWorkoutResponseApplication =
        createWorkoutService.execute(userId, createWorkoutRequestApplication);
    assertEquals(userId, createWorkoutResponseApplication.getUserId());
    assertEquals(
        createWorkoutRequestApplication.getDate(), createWorkoutResponseApplication.getDate());
    assertEquals(
        createWorkoutRequestApplication.getDescription(),
        createWorkoutResponseApplication.getDescription());
  }

  @Test
  void createExistsWorkout() {
    final UUID userId = UUID.randomUUID();
    final Date date = new Date();
    final String description = "Description example 54.";
    final CreateWorkoutRequestApplication createWorkoutRequestApplication =
        new CreateWorkoutRequestApplication();
    createWorkoutRequestApplication.setDate(date);
    createWorkoutRequestApplication.setDescription(description);

    when(workoutDao.existsWorkoutByUserAndDate(userId, createWorkoutRequestApplication.getDate()))
        .thenReturn(true);

    final DuplicatedWorkoutDateException exception =
        Assertions.assertThrows(
            DuplicatedWorkoutDateException.class,
            () -> createWorkoutService.execute(userId, createWorkoutRequestApplication));
    assertEquals(date, exception.getWorkoutDate());
    assertEquals(userId, exception.getUserId());
  }

  @Test
  void createNotPermission() {
    final UUID userId = UUID.randomUUID();
    final Date date = new Date();
    final String description = "Description example 54.";
    final CreateWorkoutRequestApplication createWorkoutRequestApplication =
        new CreateWorkoutRequestApplication();
    createWorkoutRequestApplication.setDate(date);
    createWorkoutRequestApplication.setDescription(description);

    when(workoutDao.existsWorkoutByUserAndDate(userId, createWorkoutRequestApplication.getDate()))
        .thenReturn(false);
    doThrow(new IllegalAccessException(new Workout(), AuthOperations.CREATE, userId))
        .when(authWorkoutsService)
        .checkAccess(any(Workout.class), eq(AuthOperations.CREATE));

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> createWorkoutService.execute(userId, createWorkoutRequestApplication));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertNull(exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.CREATE, exception.getAuthOperations());
  }
}
