package org.avillar.gymtracker.workoutsapi.setgroup.createsetgroup.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.exercise.application.facade.ExerciseRepositoryClient;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.CreateSetGroupServiceImpl;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.mapper.CreateSetGroupServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupResponseApplication;
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
class CreateSetGroupServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CreateSetGroupServiceImpl createSetGroupService;

  @Mock private SetGroupDao setGroupDao;
  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private CreateSetGroupServiceMapperImpl postSetGroupServiceMapper;
  @Mock private ExerciseRepositoryClient exerciseRepositoryClient;

  @Test
  void postOk() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final Workout workout = easyRandom.nextObject(Workout.class);
    final CreateSetGroupRequestApplication createSetGroupRequestApplication =
        new CreateSetGroupRequestApplication();
    createSetGroupRequestApplication.setExerciseId(setGroup.getExerciseId());
    createSetGroupRequestApplication.setDescription(setGroup.getDescription());

    when(workoutDao.getWorkoutWithSetGroupsById(setGroup.getWorkout().getId()))
        .thenReturn(List.of(workout));
    when(exerciseRepositoryClient.canAccessExerciseById(setGroup.getExerciseId())).thenReturn(true);
    Mockito.doNothing()
        .when(authWorkoutsService)
        .checkAccess(any(SetGroup.class), eq(AuthOperations.CREATE));
    when(setGroupDao.save(any(SetGroup.class))).thenAnswer(i -> i.getArguments()[0]);

    final CreateSetGroupResponseApplication result =
        createSetGroupService.execute(
            setGroup.getWorkout().getId(), createSetGroupRequestApplication);
    // assertNotNull(result.getId()); TODO Arreglar .save
    assertEquals(workout.getId(), result.getWorkout().getId());
    assertEquals(setGroup.getExerciseId(), result.getExerciseId());
    assertEquals(setGroup.getDescription(), result.getDescription());
    assertEquals(workout.getSetGroups().size(), result.getListOrder());
    verify(setGroupDao).save(any(SetGroup.class));
  }

  @Test
  void exerciseNotAccess() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    final CreateSetGroupRequestApplication createSetGroupRequestApplication =
        easyRandom.nextObject(CreateSetGroupRequestApplication.class);

    when(workoutDao.getWorkoutWithSetGroupsById(workout.getId())).thenReturn(List.of(workout));
    when(exerciseRepositoryClient.canAccessExerciseById(
            createSetGroupRequestApplication.getExerciseId()))
        .thenReturn(false);

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> createSetGroupService.execute(workout.getId(), createSetGroupRequestApplication));
    assertEquals(Exercise.class.getSimpleName(), exception.getClassName());
    assertEquals(createSetGroupRequestApplication.getExerciseId(), exception.getId());
    verify(setGroupDao, never()).save(any(SetGroup.class));

  }

  @Test
  void workoutNotFound() {
    final UUID workoutId = UUID.randomUUID();
    final CreateSetGroupRequestApplication createSetGroupRequestApplication =
        easyRandom.nextObject(CreateSetGroupRequestApplication.class);

    when(workoutDao.getWorkoutWithSetGroupsById(workoutId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> createSetGroupService.execute(workoutId, createSetGroupRequestApplication));
    assertEquals(Workout.class.getSimpleName(), exception.getClassName());
    assertEquals(workoutId, exception.getId());
    verify(setGroupDao, never()).save(any(SetGroup.class));
  }

  @Test
  void createNotPermission() {
    final UUID userId = UUID.randomUUID();
    final Workout workout = easyRandom.nextObject(Workout.class);
    final CreateSetGroupRequestApplication createSetGroupRequestApplication =
        easyRandom.nextObject(CreateSetGroupRequestApplication.class);
    final AuthOperations authOperation = AuthOperations.CREATE;

    when(workoutDao.getWorkoutWithSetGroupsById(workout.getId())).thenReturn(List.of(workout));
    when(exerciseRepositoryClient.canAccessExerciseById(
            createSetGroupRequestApplication.getExerciseId()))
        .thenReturn(true);
    doThrow(new IllegalAccessException(new SetGroup(), authOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(any(SetGroup.class), eq(authOperation));

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> createSetGroupService.execute(workout.getId(), createSetGroupRequestApplication));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertNull(exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperation, exception.getAuthOperations());
    verify(setGroupDao, never()).save(any(SetGroup.class));
  }
}
