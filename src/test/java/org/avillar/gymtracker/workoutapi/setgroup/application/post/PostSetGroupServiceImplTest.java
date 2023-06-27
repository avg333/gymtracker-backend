package org.avillar.gymtracker.workoutapi.setgroup.application.post;

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
import org.avillar.gymtracker.workoutapi.exercise.application.facade.ExerciseRepositoryClient;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.mapper.PostSetGroupServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
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
class PostSetGroupServiceImplTest {

  private PostSetGroupService postSetGroupService;

  @Mock private SetGroupDao setGroupDao;
  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private PostSetGroupServiceMapperImpl postSetGroupServiceMapper;

  @Mock private ExerciseRepositoryClient exerciseRepositoryClient;

  @BeforeEach
  void beforeEach() {
    postSetGroupService =
        new PostSetGroupServiceImpl(
            setGroupDao,
            workoutDao,
            authWorkoutsService,
            postSetGroupServiceMapper,
            exerciseRepositoryClient);
  }

  @Test
  void postOk() {
    final UUID workoutId = UUID.randomUUID();
    final PostSetGroupRequestApplication postSetGroupRequestApplication =
        new PostSetGroupRequestApplication();
    final String description = "Description example 54.";
    final UUID exerciseId = UUID.randomUUID();
    postSetGroupRequestApplication.setExerciseId(exerciseId);
    postSetGroupRequestApplication.setDescription(description);

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

    final PostSetGroupResponseApplication postSetGroupResponseApplication =
        postSetGroupService.execute(workoutId, postSetGroupRequestApplication);
    Assertions.assertEquals(workoutId, postSetGroupResponseApplication.getWorkout().getId());
    Assertions.assertEquals(exerciseId, postSetGroupResponseApplication.getExerciseId());
    Assertions.assertEquals(description, postSetGroupResponseApplication.getDescription());
    Assertions.assertEquals(setGroups.size(), postSetGroupResponseApplication.getListOrder());
  }

  @Test
  void postNotFound() {
    final UUID workoutId = UUID.randomUUID();
    final PostSetGroupRequestApplication postSetGroupRequestApplication =
        new PostSetGroupRequestApplication();
    final String description = "Description example 54.";
    final UUID exerciseId = UUID.randomUUID();
    postSetGroupRequestApplication.setExerciseId(exerciseId);
    postSetGroupRequestApplication.setDescription(description);

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
            () -> postSetGroupService.execute(workoutId, postSetGroupRequestApplication));
    assertEquals(Exercise.class.getSimpleName(), exception.getClassName());
    assertEquals(exerciseId, exception.getId());
  }

  @Test
  void exerciseNotFound() {
    final UUID workoutId = UUID.randomUUID();
    final PostSetGroupRequestApplication postSetGroupRequestApplication =
        new PostSetGroupRequestApplication();
    final String description = "Description example 54.";
    final UUID exerciseId = UUID.randomUUID();
    postSetGroupRequestApplication.setExerciseId(exerciseId);
    postSetGroupRequestApplication.setDescription(description);

    when(workoutDao.getWorkoutWithSetGroupsById(workoutId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> postSetGroupService.execute(workoutId, postSetGroupRequestApplication));
    assertEquals(Workout.class.getSimpleName(), exception.getClassName());
    assertEquals(workoutId, exception.getId());
  }

  @Test
  void deleteNotPermission() {
    final UUID userId = UUID.randomUUID();
    final UUID workoutId = UUID.randomUUID();
    final PostSetGroupRequestApplication postSetGroupRequestApplication =
        new PostSetGroupRequestApplication();
    final String description = "Description example 54.";
    final UUID exerciseId = UUID.randomUUID();
    postSetGroupRequestApplication.setExerciseId(exerciseId);
    postSetGroupRequestApplication.setDescription(description);

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
            IllegalAccessException.class, () -> postSetGroupService.execute(workoutId, postSetGroupRequestApplication));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertNull(exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.CREATE, exception.getAuthOperations());
  }
}
