package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.domain.Exercise;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.exercise.application.facade.ExerciseRepositoryClient;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.mapper.CreateSetGroupServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupResponseApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateSetGroupServiceImplTest {

  private CreateSetGroupService createSetGroupService;

  @Mock private SetGroupDao setGroupDao;
  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private CreateSetGroupServiceMapperImpl postSetGroupServiceMapper;

  @Mock private ExerciseRepositoryClient exerciseRepositoryClient;

  @BeforeEach
  void beforeEach() {
    createSetGroupService =
        new CreateSetGroupServiceImpl(
            setGroupDao,
            workoutDao,
            authWorkoutsService,
            postSetGroupServiceMapper,
            exerciseRepositoryClient);
  }

  @Test
  void postOk() {
    final UUID workoutId = UUID.randomUUID();
    final CreateSetGroupRequestApplication createSetGroupRequestApplication =
        new CreateSetGroupRequestApplication();
    final String description = "Description example 54.";
    final UUID exerciseId = UUID.randomUUID();
    createSetGroupRequestApplication.setExerciseId(exerciseId);
    createSetGroupRequestApplication.setDescription(description);

    final UUID setGroupId = UUID.randomUUID();
    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    final Workout workout = new Workout();
    workout.setId(workoutId);
    final Set<SetGroup> setGroups = Set.of(new SetGroup(), new SetGroup());
    workout.setSetGroups(setGroups);

    when(workoutDao.getWorkoutWithSetGroupsById(workoutId)).thenReturn(List.of(workout));
    when(exerciseRepositoryClient.canAccessExerciseById(exerciseId)).thenReturn(true);
    Mockito.doNothing()
        .when(authWorkoutsService)
        .checkAccess(Mockito.any(SetGroup.class), eq(AuthOperations.CREATE));
    when(setGroupDao.save(Mockito.any(SetGroup.class))).thenAnswer(i -> i.getArguments()[0]);

    final CreateSetGroupResponseApplication createSetGroupResponseApplication =
        createSetGroupService.execute(workoutId, createSetGroupRequestApplication);
    Assertions.assertEquals(workoutId, createSetGroupResponseApplication.getWorkout().getId());
    Assertions.assertEquals(exerciseId, createSetGroupResponseApplication.getExerciseId());
    Assertions.assertEquals(description, createSetGroupResponseApplication.getDescription());
    Assertions.assertEquals(setGroups.size(), createSetGroupResponseApplication.getListOrder());
  }

  @Test
  void postNotFound() {
    final UUID workoutId = UUID.randomUUID();
    final CreateSetGroupRequestApplication createSetGroupRequestApplication =
        new CreateSetGroupRequestApplication();
    final String description = "Description example 54.";
    final UUID exerciseId = UUID.randomUUID();
    createSetGroupRequestApplication.setExerciseId(exerciseId);
    createSetGroupRequestApplication.setDescription(description);

    final UUID setGroupId = UUID.randomUUID();
    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    final Workout workout = new Workout();
    workout.setId(workoutId);
    final Set<SetGroup> setGroups = Set.of(new SetGroup(), new SetGroup());
    workout.setSetGroups(setGroups);

    when(workoutDao.getWorkoutWithSetGroupsById(workoutId)).thenReturn(List.of(workout));
    when(exerciseRepositoryClient.canAccessExerciseById(exerciseId)).thenReturn(false);

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> createSetGroupService.execute(workoutId, createSetGroupRequestApplication));
    assertEquals(Exercise.class.getSimpleName(), exception.getClassName());
    assertEquals(exerciseId, exception.getId());
  }

  @Test
  void exerciseNotFound() {
    final UUID workoutId = UUID.randomUUID();
    final CreateSetGroupRequestApplication createSetGroupRequestApplication =
        new CreateSetGroupRequestApplication();
    final String description = "Description example 54.";
    final UUID exerciseId = UUID.randomUUID();
    createSetGroupRequestApplication.setExerciseId(exerciseId);
    createSetGroupRequestApplication.setDescription(description);

    when(workoutDao.getWorkoutWithSetGroupsById(workoutId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> createSetGroupService.execute(workoutId, createSetGroupRequestApplication));
    assertEquals(Workout.class.getSimpleName(), exception.getClassName());
    assertEquals(workoutId, exception.getId());
  }

  @Test
  void deleteNotPermission() {
    final UUID userId = UUID.randomUUID();
    final UUID workoutId = UUID.randomUUID();
    final CreateSetGroupRequestApplication createSetGroupRequestApplication =
        new CreateSetGroupRequestApplication();
    final String description = "Description example 54.";
    final UUID exerciseId = UUID.randomUUID();
    createSetGroupRequestApplication.setExerciseId(exerciseId);
    createSetGroupRequestApplication.setDescription(description);

    final SetGroup setGroup = new SetGroup();
    final Workout workout = new Workout();
    workout.setId(workoutId);
    final Set<SetGroup> setGroups = Set.of(new SetGroup(), new SetGroup());
    workout.setSetGroups(setGroups);

    when(workoutDao.getWorkoutWithSetGroupsById(workoutId)).thenReturn(List.of(workout));
    when(exerciseRepositoryClient.canAccessExerciseById(exerciseId)).thenReturn(true);
    doThrow(new IllegalAccessException(setGroup, AuthOperations.CREATE, userId))
        .when(authWorkoutsService)
        .checkAccess(Mockito.any(SetGroup.class), eq(AuthOperations.CREATE));

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class, () -> createSetGroupService.execute(workoutId,
                createSetGroupRequestApplication));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertNull(exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.CREATE, exception.getAuthOperations());
  }
}
