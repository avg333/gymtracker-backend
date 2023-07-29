package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.mapper.GetWorkoutSetGroupsServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.model.GetWorkoutSetGroupsResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class GetWorkoutSetGroupsServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetWorkoutSetGroupsServiceImpl getWorkoutSetGroupsService;

  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private GetWorkoutSetGroupsServiceMapperImpl getWorkoutSetGroupsServiceMapper;

  @Test
  void getOk() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    workout.setSetGroups(easyRandom.objects(SetGroup.class, 5).collect(Collectors.toSet()));

    when(workoutDao.getWorkoutWithSetGroupsById(workout.getId())).thenReturn(List.of(workout));
    doNothing().when(authWorkoutsService).checkAccess(workout, AuthOperations.READ);

    final GetWorkoutSetGroupsResponseApplication result =
        getWorkoutSetGroupsService.execute(workout.getId());
    assertThat(result).usingRecursiveComparison().isEqualTo(workout);
  }

  @Test
  void getNotFound() {
    final UUID workoutId = UUID.randomUUID();

    when(workoutDao.getWorkoutWithSetGroupsById(workoutId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class, () -> getWorkoutSetGroupsService.execute(workoutId));
    assertEquals(Workout.class.getSimpleName(), exception.getClassName());
    assertEquals(workoutId, exception.getId());
  }

  @Test
  void getNotPermission() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    final UUID userId = UUID.randomUUID();
    final AuthOperations readOperation = AuthOperations.READ;

    when(workoutDao.getWorkoutWithSetGroupsById(workout.getId())).thenReturn(List.of(workout));
    doThrow(new IllegalAccessException(workout, readOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(workout, readOperation);

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class,
            () -> getWorkoutSetGroupsService.execute(workout.getId()));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workout.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(readOperation, exception.getAuthOperations());
  }
}
