package org.avillar.gymtracker.workoutapi.workout.getworkoutdateandid.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Data;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDateAndId;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.application.GetWorkoutsDateAndIdServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class GetWorkoutsDateAndIdServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetWorkoutsDateAndIdServiceImpl getWorkoutIdAndDateService;

  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void getAllUserWorkoutDates() {
    final UUID userId = UUID.randomUUID();
    final List<WorkoutDateAndId> expected = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      expected.add(new Results());
    }

    doNothing()
        .when(authWorkoutsService)
        .checkAccess(Mockito.any(Workout.class), eq(AuthOperations.READ));
    when(workoutDao.getWorkoutsIdAndDatesByUser(userId)).thenReturn(expected);

    final Map<Date, UUID> results = getWorkoutIdAndDateService.execute(userId, null);
    assertEquals(expected.size(), results.size());
    for (final WorkoutDateAndId workoutDateAndId : expected) {
      assertTrue(results.containsKey(workoutDateAndId.getDate()));
      assertEquals(workoutDateAndId.getId(), results.get(workoutDateAndId.getDate()));
    }
    verify(workoutDao).getWorkoutsIdAndDatesByUser(userId);
    verify(workoutDao, never()).getWorkoutsIdAndDatesByUserAndExercise(any(), any());
  }

  @Test
  void getAllUserWorkoutsWithExercise() {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final List<WorkoutDateAndId> expected = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      expected.add(new Results());
    }

    doNothing()
        .when(authWorkoutsService)
        .checkAccess(Mockito.any(Workout.class), eq(AuthOperations.READ));
    when(workoutDao.getWorkoutsIdAndDatesByUserAndExercise(userId, exerciseId))
        .thenReturn(expected);

    final Map<Date, UUID> results = getWorkoutIdAndDateService.execute(userId, exerciseId);
    assertEquals(expected.size(), results.size());
    for (final WorkoutDateAndId workoutDateAndId : expected) {
      assertTrue(results.containsKey(workoutDateAndId.getDate()));
      assertEquals(workoutDateAndId.getId(), results.get(workoutDateAndId.getDate()));
    }
    verify(workoutDao, never()).getWorkoutsIdAndDatesByUser(any());
    verify(workoutDao).getWorkoutsIdAndDatesByUserAndExercise(userId, exerciseId);
  }

  @Test
  void getAllUserWorkoutDatesNoPermission() {
    final UUID userId = UUID.randomUUID();
    final AuthOperations readOperation = AuthOperations.READ;

    doThrow(new IllegalAccessException(new Workout(), readOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(any(Workout.class), eq(readOperation));

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class, () -> getWorkoutIdAndDateService.execute(userId, null));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertNull(exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(readOperation, exception.getAuthOperations());
    verify(workoutDao, never()).getWorkoutsIdAndDatesByUser(any());
    verify(workoutDao, never()).getWorkoutsIdAndDatesByUserAndExercise(any(), any());
  }

  @Test
  void getAllUserWorkoutsWithExerciseNoPermission() {
    final UUID userId = UUID.randomUUID();
    final AuthOperations readOperation = AuthOperations.READ;

    doThrow(new IllegalAccessException(new Workout(), readOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(any(Workout.class), eq(readOperation));

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class,
            () -> getWorkoutIdAndDateService.execute(userId, UUID.randomUUID()));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertNull(exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(readOperation, exception.getAuthOperations());
    verify(workoutDao, never()).getWorkoutsIdAndDatesByUser(any());
    verify(workoutDao, never()).getWorkoutsIdAndDatesByUserAndExercise(any(), any());
  }

  @Data
  private class Results implements WorkoutDateAndId {
    private final Date date = easyRandom.nextObject(Date.class);
    private final UUID id = UUID.randomUUID();
  }
}
