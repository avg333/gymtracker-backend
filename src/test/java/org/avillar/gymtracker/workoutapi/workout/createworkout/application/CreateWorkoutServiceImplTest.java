package org.avillar.gymtracker.workoutapi.workout.createworkout.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutForDateAlreadyExistsException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
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
class CreateWorkoutServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.CREATE;

  @InjectMocks private CreateWorkoutServiceImpl createWorkoutService;

  @Mock private WorkoutFacade workoutFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void shouldCreateWorkoutSuccessfully()
      throws WorkoutForDateAlreadyExistsException, WorkoutIllegalAccessException {
    final UUID userId = UUID.randomUUID();
    final Workout newWorkout = Instancio.create(Workout.class);
    final Workout savedWorkout = Instancio.create(Workout.class);

    final ArgumentCaptor<Workout> workoutArgumentCaptorBeforeAuth =
        ArgumentCaptor.forClass(Workout.class);
    final ArgumentCaptor<Workout> workoutArgumentCaptorBeforeSave =
        ArgumentCaptor.forClass(Workout.class);

    doNothing()
        .when(authWorkoutsService)
        .checkAccess(workoutArgumentCaptorBeforeAuth.capture(), eq(AUTH_OPERATIONS));
    when(workoutFacade.existsWorkoutByUserAndDate(userId, newWorkout.getDate())).thenReturn(false);
    when(workoutFacade.saveWorkout(workoutArgumentCaptorBeforeSave.capture()))
        .thenReturn(savedWorkout);

    assertThat(createWorkoutService.execute(userId, newWorkout)).isEqualTo(savedWorkout);

    final Workout workoutBeforeAuth = workoutArgumentCaptorBeforeAuth.getValue();
    assertThat(workoutBeforeAuth)
        .isNotNull()
        .usingRecursiveComparison()
        .ignoringFields("userId")
        .isEqualTo(newWorkout);
    assertThat(workoutBeforeAuth.getUserId()).isEqualTo(userId);

    assertThat(workoutArgumentCaptorBeforeSave.getValue()).isEqualTo(newWorkout);

    verify(workoutFacade).existsWorkoutByUserAndDate(userId, newWorkout.getDate());
    verify(workoutFacade).saveWorkout(newWorkout);
  }

  @Test
  void shouldThrowWorkoutForDateAlreadyExistsExceptionWhenDateIsAlreadyInUse()
      throws WorkoutIllegalAccessException {
    final UUID userId = UUID.randomUUID();
    final Workout newWorkout = Instancio.create(Workout.class);
    final WorkoutForDateAlreadyExistsException exception =
        new WorkoutForDateAlreadyExistsException(userId, newWorkout.getDate());

    doNothing().when(authWorkoutsService).checkAccess(newWorkout, AUTH_OPERATIONS);
    when(workoutFacade.existsWorkoutByUserAndDate(userId, newWorkout.getDate())).thenReturn(true);

    assertThatThrownBy(() -> createWorkoutService.execute(userId, newWorkout)).isEqualTo(exception);

    verify(workoutFacade).existsWorkoutByUserAndDate(userId, newWorkout.getDate());
    verify(workoutFacade, never()).saveWorkout(any());
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToCreateWorkout()
      throws WorkoutIllegalAccessException {
    final UUID userId = UUID.randomUUID();
    final Workout newWorkout = Instancio.create(Workout.class);
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    doThrow(exception).when(authWorkoutsService).checkAccess(newWorkout, AUTH_OPERATIONS);

    assertThatThrownBy(() -> createWorkoutService.execute(userId, newWorkout)).isEqualTo(exception);

    verify(workoutFacade, never()).existsWorkoutByUserAndDate(any(), any());
    verify(workoutFacade, never()).saveWorkout(any());
  }
}
