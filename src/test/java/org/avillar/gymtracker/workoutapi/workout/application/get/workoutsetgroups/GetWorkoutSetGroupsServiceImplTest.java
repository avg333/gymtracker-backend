package org.avillar.gymtracker.workoutapi.workout.application.get.workoutsetgroups;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.application.get.workoutsetgroups.mapper.GetWorkoutSetGroupsServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.application.get.workoutsetgroups.model.GetWorkoutSetGroupsResponseApplication;
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
class GetWorkoutSetGroupsServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  private GetWorkoutSetGroupsService getWorkoutSetGroupsService;

  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private GetWorkoutSetGroupsServiceMapperImpl getWorkoutSetGroupsServiceMapper;

  @BeforeEach
  void beforeEach() {
    getWorkoutSetGroupsService =
        new GetWorkoutSetGroupsServiceImpl(
            workoutDao, authWorkoutsService, getWorkoutSetGroupsServiceMapper);
  }

  @Test
  void getOk() {
    final Workout workout = easyRandom.nextObject(Workout.class);

    when(workoutDao.getWorkoutWithSetGroupsById(workout.getId())).thenReturn(List.of(workout));
    Mockito.doNothing()
        .when(authWorkoutsService)
        .checkAccess(Mockito.any(Workout.class), eq(AuthOperations.READ));

    final GetWorkoutSetGroupsResponseApplication getWorkoutSetGroupsResponseApplication =
        getWorkoutSetGroupsService.execute(workout.getId());
    Assertions.assertEquals(workout.getId(), getWorkoutSetGroupsResponseApplication.getId());
    Assertions.assertEquals(workout.getDate(), getWorkoutSetGroupsResponseApplication.getDate());
    Assertions.assertEquals(
        workout.getDescription(), getWorkoutSetGroupsResponseApplication.getDescription());
    Assertions.assertEquals(
        workout.getUserId(), getWorkoutSetGroupsResponseApplication.getUserId());
    Assertions.assertEquals(
        workout.getSetGroups().size(),
        getWorkoutSetGroupsResponseApplication.getSetGroups().size());
  }

  @Test
  void getNotFound() {
    final UUID workoutId = UUID.randomUUID();

    when(workoutDao.getWorkoutWithSetGroupsById(workoutId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class, () -> getWorkoutSetGroupsService.execute(workoutId));
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

    when(workoutDao.getWorkoutWithSetGroupsById(workout.getId())).thenReturn(List.of(workout));
    doThrow(new IllegalAccessException(workout, AuthOperations.READ, userId))
        .when(authWorkoutsService)
        .checkAccess(workout, AuthOperations.READ);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class, () -> getWorkoutSetGroupsService.execute(workoutId));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workoutId, exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.READ, exception.getAuthOperations());
  }
}
