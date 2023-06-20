package org.avillar.gymtracker.workoutapi.workout.application.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.application.post.mapper.PostWorkoutServiceMapper;
import org.avillar.gymtracker.workoutapi.workout.application.post.mapper.PostWorkoutServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutRequestApplication;
import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostWorkoutServiceImplTest {

  private PostWorkoutServiceImpl postWorkoutService;

  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private PostWorkoutServiceMapperImpl postWorkoutServiceMapper;

  @BeforeEach
  void beforeEach() {
    postWorkoutService =
        new PostWorkoutServiceImpl(workoutDao, authWorkoutsService, postWorkoutServiceMapper);
  }

  @Test
  void postOk() {
    final UUID userId = UUID.randomUUID();
    final Date date = new Date();
    final String description = "Description example 54.";
    final PostWorkoutRequestApplication postWorkoutRequestApplication =
        new PostWorkoutRequestApplication();
    postWorkoutRequestApplication.setDate(date);
    postWorkoutRequestApplication.setDescription(description);

    when(workoutDao.existsWorkoutByUserAndDate(userId, postWorkoutRequestApplication.getDate()))
        .thenReturn(false);
    Mockito.doNothing()
        .when(authWorkoutsService)
        .checkAccess(Mockito.any(Workout.class), eq(AuthOperations.CREATE));
    when(workoutDao.save(Mockito.any(Workout.class))).thenAnswer(i -> i.getArguments()[0]);

    final PostWorkoutResponseApplication postWorkoutResponseApplication =
        postWorkoutService.post(userId, postWorkoutRequestApplication);
    Assertions.assertEquals(userId, postWorkoutResponseApplication.getUserId());
    Assertions.assertEquals(date, postWorkoutResponseApplication.getDate());
    Assertions.assertEquals(description, postWorkoutResponseApplication.getDescription());
  }

  @Test
  void postExistsWorkout() {
    final UUID userId = UUID.randomUUID();
    final Date date = new Date();
    final String description = "Description example 54.";
    final PostWorkoutRequestApplication postWorkoutRequestApplication =
        new PostWorkoutRequestApplication();
    postWorkoutRequestApplication.setDate(date);
    postWorkoutRequestApplication.setDescription(description);

    when(workoutDao.existsWorkoutByUserAndDate(userId, postWorkoutRequestApplication.getDate()))
        .thenReturn(true);

    final DuplicatedWorkoutDateException exception =
        Assertions.assertThrows(
            DuplicatedWorkoutDateException.class,
            () -> postWorkoutService.post(userId, postWorkoutRequestApplication));
    assertEquals(date, exception.getWorkoutDate());
    assertEquals(userId, exception.getUserId());
  }

  @Test
  void postNotPermission() {
    final UUID userId = UUID.randomUUID();
    final Date date = new Date();
    final String description = "Description example 54.";
    final PostWorkoutRequestApplication postWorkoutRequestApplication =
        new PostWorkoutRequestApplication();
    postWorkoutRequestApplication.setDate(date);
    postWorkoutRequestApplication.setDescription(description);

    when(workoutDao.existsWorkoutByUserAndDate(userId, postWorkoutRequestApplication.getDate()))
        .thenReturn(false);
    doThrow(new IllegalAccessException(new Workout(), AuthOperations.CREATE, userId))
        .when(authWorkoutsService)
        .checkAccess(any(Workout.class), eq(AuthOperations.CREATE));

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> postWorkoutService.post(userId, postWorkoutRequestApplication));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertNull(exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.CREATE, exception.getAuthOperations());
  }
}
