package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateWorkoutDateServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UpdateWorkoutDateServiceImpl updateWorkoutDateService;

  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void postOk() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    workout.setDate(new GregorianCalendar(2014, Calendar.FEBRUARY, 12).getTime());
    final Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();

    when(workoutDao.findById(workout.getId())).thenReturn(Optional.of(workout));
    doNothing().when(authWorkoutsService).checkAccess(workout, AuthOperations.UPDATE);
    when(workoutDao.save(workout)).thenAnswer(i -> i.getArguments()[0]);

    final Date result = updateWorkoutDateService.execute(workout.getId(), date);
    assertEquals(date, result);
    verify(workoutDao).save(workout);
  }

  @Test
  void postSameDate() {
    final Workout workout = easyRandom.nextObject(Workout.class);

    when(workoutDao.findById(workout.getId())).thenReturn(Optional.of(workout));
    doNothing().when(authWorkoutsService).checkAccess(workout, AuthOperations.UPDATE);

    assertEquals(
        workout.getDate(), updateWorkoutDateService.execute(workout.getId(), workout.getDate()));
    verify(workoutDao, never()).save(any());
  }

  @Test
  void postExistsWorkout() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    workout.setDate(new GregorianCalendar(2014, Calendar.FEBRUARY, 12).getTime());
    final Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();

    when(workoutDao.findById(workout.getId())).thenReturn(Optional.of(workout));
    doNothing().when(authWorkoutsService).checkAccess(workout, AuthOperations.UPDATE);
    when(workoutDao.existsWorkoutByUserAndDate(workout.getUserId(), date)).thenReturn(true);

    final DuplicatedWorkoutDateException exception =
        assertThrows(
            DuplicatedWorkoutDateException.class,
            () -> updateWorkoutDateService.execute(workout.getId(), date));
    assertEquals(date, exception.getWorkoutDate());
    assertEquals(workout.getUserId(), exception.getUserId());
    verify(workoutDao, never()).save(any());
  }

  @Test
  void updateNotFound() {
    final UUID workoutId = UUID.randomUUID();

    when(workoutDao.findById(workoutId))
        .thenThrow(new EntityNotFoundException(Workout.class, workoutId));

    final EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class,
            () -> updateWorkoutDateService.execute(workoutId, new Date()));
    assertEquals(Workout.class.getSimpleName(), exception.getClassName());
    assertEquals(workoutId, exception.getId());
    verify(workoutDao, never()).save(any());
  }

  @Test
  void updateNotPermission() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    workout.setDate(new GregorianCalendar(2014, Calendar.FEBRUARY, 12).getTime());
    final Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
    final UUID userId = UUID.randomUUID();
    final AuthOperations updateOperation = AuthOperations.UPDATE;

    when(workoutDao.findById(workout.getId())).thenReturn(Optional.of(workout));
    doThrow(new IllegalAccessException(workout, updateOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(workout, updateOperation);

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class,
            () -> updateWorkoutDateService.execute(workout.getId(), date));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workout.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(updateOperation, exception.getAuthOperations());
    verify(workoutDao, never()).save(any());
  }
}
