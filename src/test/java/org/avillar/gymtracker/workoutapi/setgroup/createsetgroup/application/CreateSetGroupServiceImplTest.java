package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
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
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException.AccessError;
import org.avillar.gymtracker.workoutapi.exercise.application.facade.ExerciseRepositoryClient;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.mapper.CreateSetGroupServiceMapper;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class CreateSetGroupServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CreateSetGroupServiceImpl createSetGroupService;

  @Mock private SetGroupDao setGroupDao;
  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Spy
  private final CreateSetGroupServiceMapper postSetGroupServiceMapper =
      Mappers.getMapper(CreateSetGroupServiceMapper.class);

  @Mock private ExerciseRepositoryClient exerciseRepositoryClient;

  @Test
  void postOk() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final CreateSetGroupRequestApplication createSetGroupRequestApplication =
        new CreateSetGroupRequestApplication();
    createSetGroupRequestApplication.setExerciseId(setGroup.getExerciseId());
    createSetGroupRequestApplication.setDescription(setGroup.getDescription());

    when(workoutDao.getWorkoutWithSetGroupsById(setGroup.getWorkout().getId()))
        .thenReturn(List.of(setGroup.getWorkout()));
    doNothing()
        .when(authWorkoutsService)
        .checkAccess(any(SetGroup.class), eq(AuthOperations.CREATE)); // FIXME Avoid any
    doNothing().when(exerciseRepositoryClient).checkExerciseAccessById(setGroup.getExerciseId());
    when(setGroupDao.save(any(SetGroup.class))).thenAnswer(i -> i.getArguments()[0]);

    final CreateSetGroupResponseApplication result =
        createSetGroupService.execute(
            setGroup.getWorkout().getId(), createSetGroupRequestApplication);
    // assertNotNull(result.getId()); TODO Fix .save
    assertEquals(setGroup.getWorkout().getSetGroups().size(), result.getListOrder());
    assertThat(result)
        .usingRecursiveComparison()
        .ignoringFields("id", "listOrder")
        .isEqualTo(setGroup); // FIXME
    verify(setGroupDao).save(any(SetGroup.class)); // FIXME
  }

  @Test
  void exerciseNotFound() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    final CreateSetGroupRequestApplication createSetGroupRequestApplication =
        easyRandom.nextObject(CreateSetGroupRequestApplication.class);
    final AccessError notFound = AccessError.NOT_FOUND;

    when(workoutDao.getWorkoutWithSetGroupsById(workout.getId())).thenReturn(List.of(workout));
    doNothing()
        .when(authWorkoutsService)
        .checkAccess(any(SetGroup.class), eq(AuthOperations.CREATE));
    doThrow(
            new ExerciseNotFoundException(
                createSetGroupRequestApplication.getExerciseId(), notFound))
        .when(exerciseRepositoryClient)
        .checkExerciseAccessById(createSetGroupRequestApplication.getExerciseId());

    final ExerciseNotFoundException exception =
        assertThrows(
            ExerciseNotFoundException.class,
            () -> createSetGroupService.execute(workout.getId(), createSetGroupRequestApplication));
    assertEquals(createSetGroupRequestApplication.getExerciseId(), exception.getId());
    assertEquals(notFound, exception.getAccessError());
    verify(setGroupDao, never()).save(any());
  }

  @Test
  void exerciseNotAccess() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    final CreateSetGroupRequestApplication createSetGroupRequestApplication =
        easyRandom.nextObject(CreateSetGroupRequestApplication.class);
    final AccessError notAccess = AccessError.NOT_ACCESS;

    when(workoutDao.getWorkoutWithSetGroupsById(workout.getId())).thenReturn(List.of(workout));
    doNothing()
        .when(authWorkoutsService)
        .checkAccess(any(SetGroup.class), eq(AuthOperations.CREATE));
    doThrow(
            new ExerciseNotFoundException(
                createSetGroupRequestApplication.getExerciseId(), notAccess))
        .when(exerciseRepositoryClient)
        .checkExerciseAccessById(createSetGroupRequestApplication.getExerciseId());

    final ExerciseNotFoundException exception =
        assertThrows(
            ExerciseNotFoundException.class,
            () -> createSetGroupService.execute(workout.getId(), createSetGroupRequestApplication));
    assertEquals(createSetGroupRequestApplication.getExerciseId(), exception.getId());
    assertEquals(notAccess, exception.getAccessError());
    verify(setGroupDao, never()).save(any());
  }

  @Test
  void workoutNotFound() {
    final UUID workoutId = UUID.randomUUID();

    when(workoutDao.getWorkoutWithSetGroupsById(workoutId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class,
            () ->
                createSetGroupService.execute(
                    workoutId, easyRandom.nextObject(CreateSetGroupRequestApplication.class)));
    assertEquals(Workout.class.getSimpleName(), exception.getClassName());
    assertEquals(workoutId, exception.getId());
    verify(setGroupDao, never()).save(any());
  }

  @Test
  void createNotPermission() {
    final UUID userId = UUID.randomUUID();
    final Workout workout = easyRandom.nextObject(Workout.class);
    final AuthOperations createOperation = AuthOperations.CREATE;

    when(workoutDao.getWorkoutWithSetGroupsById(workout.getId())).thenReturn(List.of(workout));
    doThrow(new IllegalAccessException(new SetGroup(), createOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(any(SetGroup.class), eq(createOperation));

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class,
            () ->
                createSetGroupService.execute(
                    workout.getId(),
                    easyRandom.nextObject(CreateSetGroupRequestApplication.class)));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertNull(exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(createOperation, exception.getAuthOperations());
    verify(setGroupDao, never()).save(any());
  }
}
