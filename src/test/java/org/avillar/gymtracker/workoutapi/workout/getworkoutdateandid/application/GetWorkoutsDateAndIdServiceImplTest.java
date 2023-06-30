package org.avillar.gymtracker.workoutapi.workout.getworkoutdateandid.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDateAndId;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.application.GetWorkoutsDateAndIdServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetWorkoutsDateAndIdServiceImplTest {

  @InjectMocks private GetWorkoutsDateAndIdServiceImpl getWorkoutIdAndDateService;

  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void getAllUserWorkoutDates() {
    final UUID userId = UUID.randomUUID();
    final UUID workoutId = UUID.randomUUID();
    final Date workoutDate = new Date();
    final List<WorkoutDateAndId> expectedResults = new ArrayList<>();
    expectedResults.add(new Results(workoutDate, workoutId));

    Mockito.doNothing()
        .when(authWorkoutsService)
        .checkAccess(Mockito.any(Workout.class), eq(AuthOperations.READ));
    when(workoutDao.getWorkoutsIdAndDatesByUser(userId)).thenReturn(expectedResults);

    final Map<Date, UUID> results = getWorkoutIdAndDateService.execute(userId, null);
    assertEquals(expectedResults.size(), results.size());
    assertTrue(results.containsKey(workoutDate));
    assertEquals(workoutId, results.get(workoutDate));
    verify(workoutDao).getWorkoutsIdAndDatesByUser(userId);
    verify(workoutDao, never()).getWorkoutsIdAndDatesByUserAndExercise(any(), any());
  }

  @Test
  void getAllUserWorkoutsWithExercise() {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final UUID workoutId = UUID.randomUUID();
    final Date workoutDate = new Date();
    final List<WorkoutDateAndId> expectedResults = new ArrayList<>();
    expectedResults.add(new Results(workoutDate, workoutId));

    Mockito.doNothing()
        .when(authWorkoutsService)
        .checkAccess(Mockito.any(Workout.class), eq(AuthOperations.READ));
    when(workoutDao.getWorkoutsIdAndDatesByUserAndExercise(userId, exerciseId))
        .thenReturn(expectedResults);

    final Map<Date, UUID> results = getWorkoutIdAndDateService.execute(userId, exerciseId);
    assertEquals(expectedResults.size(), results.size());
    assertTrue(results.containsKey(workoutDate));
    assertEquals(workoutId, results.get(workoutDate));
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
    final UUID exerciseId = UUID.randomUUID();
    final AuthOperations readOperation = AuthOperations.READ;

    doThrow(new IllegalAccessException(new Workout(), readOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(any(Workout.class), eq(readOperation));

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> getWorkoutIdAndDateService.execute(userId, exerciseId));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertNull(exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(readOperation, exception.getAuthOperations());
    verify(workoutDao, never()).getWorkoutsIdAndDatesByUser(any());
    verify(workoutDao, never()).getWorkoutsIdAndDatesByUserAndExercise(any(), any());
  }

  @Data
  @AllArgsConstructor
  private static class Results implements WorkoutDateAndId {
    private Date date;
    private UUID id;
  }
}
