package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
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
class UpdateWorkoutDescriptionServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.UPDATE;

  @InjectMocks private UpdateWorkoutDescriptionServiceImpl updateWorkoutDescriptionService;

  @Mock private WorkoutFacade workoutFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void shouldUpdateDescriptionSuccessfully()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    final String description = Instancio.create(String.class);
    final Workout workout = Instancio.create(Workout.class);
    final Workout savedWorkout = Instancio.create(Workout.class);

    final ArgumentCaptor<Workout> workoutArgumentCaptor = ArgumentCaptor.forClass(Workout.class);

    when(workoutFacade.getWorkout(workout.getId())).thenReturn(workout);
    doNothing().when(authWorkoutsService).checkAccess(workout, AUTH_OPERATIONS);
    when(workoutFacade.saveWorkout(workoutArgumentCaptor.capture())).thenReturn(savedWorkout);

    assertThat(updateWorkoutDescriptionService.execute(workout.getId(), description))
        .isEqualTo(savedWorkout.getDescription());

    final Workout workoutArgumentCaptorValue = workoutArgumentCaptor.getValue();
    assertThat(workoutArgumentCaptorValue)
        .isNotNull()
        .usingRecursiveComparison()
        .ignoringFields("description")
        .isEqualTo(workout);
    assertThat(workoutArgumentCaptorValue.getDescription()).isEqualTo(workout.getDescription());

    verify(workoutFacade).saveWorkout(workoutArgumentCaptorValue);
  }

  @Test
  void shouldNotUpdateDescriptionWhenSameAsBefore()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    final Workout workout = Instancio.create(Workout.class);
    final String description = workout.getDescription();

    when(workoutFacade.getWorkout(workout.getId())).thenReturn(workout);
    doNothing().when(authWorkoutsService).checkAccess(workout, AUTH_OPERATIONS);

    assertThat(updateWorkoutDescriptionService.execute(workout.getId(), description))
        .isEqualTo(description);

    verify(workoutFacade, never()).saveWorkout(any());
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToUpdateWorkout()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    final String description = Instancio.create(String.class);
    final Workout workout = Instancio.create(Workout.class);
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(workoutFacade.getWorkout(workout.getId())).thenReturn(workout);
    doThrow(exception).when(authWorkoutsService).checkAccess(workout, AUTH_OPERATIONS);

    assertThatThrownBy(() -> updateWorkoutDescriptionService.execute(workout.getId(), description))
        .isEqualTo(exception);

    verify(workoutFacade, never()).saveWorkout(any());
  }

  @Test
  void shouldThrowEntityNotFoundExceptionWhenGettingNonExistentWorkout()
      throws WorkoutNotFoundException {
    final String description = Instancio.create(String.class);
    final UUID workoutId = UUID.randomUUID();
    final WorkoutNotFoundException exception =
        ExceptionGenerator.generateWorkoutNotFoundException();

    doThrow(exception).when(workoutFacade).getWorkout(workoutId);

    assertThatThrownBy(() -> updateWorkoutDescriptionService.execute(workoutId, description))
        .isEqualTo(exception);

    verify(workoutFacade, never()).saveWorkout(any());
  }
}
