package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateWorkoutDateServiceImplTest {

  private UpdateWorkoutDateServiceImpl updateWorkoutDateService;

  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @BeforeEach
  void beforeEach() {
    updateWorkoutDateService = new UpdateWorkoutDateServiceImpl(workoutDao, authWorkoutsService);
  }

  @Test
  void postOk() {
    final UUID workoutId = UUID.randomUUID();
    final Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();

    final Workout workout = new Workout();
    workout.setDate(new GregorianCalendar(2014, Calendar.FEBRUARY, 12).getTime());

    when(workoutDao.findById(workoutId)).thenReturn(Optional.of(workout));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(workout, AuthOperations.UPDATE);
    when(workoutDao.save(Mockito.any(Workout.class))).thenAnswer(i -> i.getArguments()[0]);

    assertEquals(date, updateWorkoutDateService.execute(workoutId, date));
  }

  @Test
  void postSameDate() {
    final UUID workoutId = UUID.randomUUID();
    final Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();

    final Workout workout = new Workout();
    workout.setDate(date);

    when(workoutDao.findById(workoutId)).thenReturn(Optional.of(workout));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(workout, AuthOperations.UPDATE);

    verify(workoutDao, never()).save(Mockito.any(Workout.class));
    assertEquals(date, updateWorkoutDateService.execute(workoutId, date));
  }

  @Test
  void updateNotFound() {
    final UUID workoutId = UUID.randomUUID();
    final Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();

    when(workoutDao.findById(workoutId))
        .thenThrow(new EntityNotFoundException(Workout.class, workoutId));

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class, () -> updateWorkoutDateService.execute(workoutId, date));
    assertEquals(Workout.class.getSimpleName(), exception.getClassName());
    assertEquals(workoutId, exception.getId());
  }

  @Test
  void updateNotPermission() {
    final UUID workoutId = UUID.randomUUID();
    final Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
    final Workout workout = new Workout();
    final UUID userId = UUID.randomUUID();
    workout.setId(workoutId);
    workout.setUserId(userId);

    when(workoutDao.findById(workoutId)).thenReturn(Optional.of(workout));
    doThrow(new IllegalAccessException(workout, AuthOperations.UPDATE, userId))
        .when(authWorkoutsService)
        .checkAccess(workout, AuthOperations.UPDATE);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class, () -> updateWorkoutDateService.execute(workoutId, date));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workoutId, exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.UPDATE, exception.getAuthOperations());
  }

  @Test
  void postExistsWorkout() {
    final UUID workoutId = UUID.randomUUID();
    final Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
    final Workout workout = new Workout();
    final UUID userId = UUID.randomUUID();
    workout.setDate(new GregorianCalendar(2014, Calendar.FEBRUARY, 12).getTime());
    workout.setId(workoutId);
    workout.setUserId(userId);

    when(workoutDao.findById(workoutId)).thenReturn(Optional.of(workout));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(workout, AuthOperations.UPDATE);
    when(workoutDao.existsWorkoutByUserAndDate(userId, date))
        .thenReturn(true);

    final DuplicatedWorkoutDateException exception =
        Assertions.assertThrows(
            DuplicatedWorkoutDateException.class,
            () -> updateWorkoutDateService.execute(workoutId, date));
    assertEquals(date, exception.getWorkoutDate());
    assertEquals(userId, exception.getUserId());
  }
}
