package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutForDateAlreadyExistsException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.workout.WorkoutFacade;
import org.avillar.gymtracker.workoutapi.common.utils.ExceptionGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class UpdateWorkoutDateServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.UPDATE;

  @InjectMocks private UpdateWorkoutDateServiceImpl updateWorkoutDateService;

  @Mock private WorkoutFacade workoutFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void shouldUpdateDateSuccessfully()
      throws WorkoutNotFoundException,
          WorkoutIllegalAccessException,
          WorkoutForDateAlreadyExistsException {
    final LocalDate date = Instancio.create(LocalDate.class);
    final Workout workout = Instancio.create(Workout.class);
    final Workout savedWorkout = Instancio.create(Workout.class);

    final ArgumentCaptor<Workout> workoutArgumentCaptor = ArgumentCaptor.forClass(Workout.class);

    when(workoutFacade.getWorkout(workout.getId())).thenReturn(workout);
    doNothing().when(authWorkoutsService).checkAccess(workout, AUTH_OPERATIONS);
    when(workoutFacade.existsWorkoutByUserAndDate(workout.getUserId(), date)).thenReturn(false);
    when(workoutFacade.saveWorkout(workoutArgumentCaptor.capture())).thenReturn(savedWorkout);

    assertThat(updateWorkoutDateService.execute(workout.getId(), date))
        .isEqualTo(savedWorkout.getDate());

    final Workout workoutArgumentCaptorValue = workoutArgumentCaptor.getValue();
    assertThat(workoutArgumentCaptorValue)
        .isNotNull()
        .usingRecursiveComparison()
        .ignoringFields("date")
        .isEqualTo(workout);
    assertThat(workoutArgumentCaptorValue.getDate()).isEqualTo(workout.getDate());

    verify(workoutFacade).existsWorkoutByUserAndDate(workout.getUserId(), date);
    verify(workoutFacade).saveWorkout(workoutArgumentCaptorValue);
  }

  @Test
  void shouldNotUpdateDateWhenDateIsSameAsBefore()
      throws WorkoutNotFoundException,
          WorkoutIllegalAccessException,
          WorkoutForDateAlreadyExistsException {
    final Workout workout = Instancio.create(Workout.class);
    final LocalDate date = workout.getDate();

    when(workoutFacade.getWorkout(workout.getId())).thenReturn(workout);
    doNothing().when(authWorkoutsService).checkAccess(workout, AUTH_OPERATIONS);

    assertThat(updateWorkoutDateService.execute(workout.getId(), date)).isEqualTo(date);

    verify(workoutFacade, never()).existsWorkoutByUserAndDate(any(), any());
    verify(workoutFacade, never()).saveWorkout(any());
  }

  @Test
  void shouldThrowDuplicatedWorkoutDateExceptionWhenExistsWorkoutForUserAndDate()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    final LocalDate date = Instancio.create(LocalDate.class);
    final Workout workout = Instancio.create(Workout.class);
    final WorkoutForDateAlreadyExistsException exception =
        new WorkoutForDateAlreadyExistsException(workout.getUserId(), date);

    when(workoutFacade.getWorkout(workout.getId())).thenReturn(workout);
    doNothing().when(authWorkoutsService).checkAccess(workout, AUTH_OPERATIONS);
    when(workoutFacade.existsWorkoutByUserAndDate(workout.getUserId(), date)).thenReturn(true);

    assertThatThrownBy(() -> updateWorkoutDateService.execute(workout.getId(), date))
        .isEqualTo(exception);

    verify(workoutFacade).existsWorkoutByUserAndDate(workout.getUserId(), date);
    verify(workoutFacade, never()).saveWorkout(any());
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToUpdateWorkout()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    final LocalDate date = Instancio.create(LocalDate.class);
    final Workout workout = Instancio.create(Workout.class);
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(workoutFacade.getWorkout(workout.getId())).thenReturn(workout);
    doThrow(exception).when(authWorkoutsService).checkAccess(workout, AUTH_OPERATIONS);

    assertThatThrownBy(() -> updateWorkoutDateService.execute(workout.getId(), date))
        .isEqualTo(exception);

    verify(workoutFacade, never()).existsWorkoutByUserAndDate(any(), any());
    verify(workoutFacade, never()).saveWorkout(any());
  }

  @Test
  void shouldThrowEntityNotFoundExceptionWhenGettingNonExistentWorkout()
      throws WorkoutNotFoundException {
    final LocalDate date = Instancio.create(LocalDate.class);
    final UUID workoutId = UUID.randomUUID();
    final WorkoutNotFoundException exception =
        ExceptionGenerator.generateWorkoutNotFoundException();

    doThrow(exception).when(workoutFacade).getWorkout(workoutId);

    assertThatThrownBy(() -> updateWorkoutDateService.execute(workoutId, date))
        .isEqualTo(exception);

    verify(workoutFacade, never()).existsWorkoutByUserAndDate(any(), any());
    verify(workoutFacade, never()).saveWorkout(any());
  }
}
