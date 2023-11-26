package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class GetWorkoutSetGroupsServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.READ;

  @InjectMocks private GetWorkoutSetGroupsServiceImpl getWorkoutSetGroupsService;

  @Mock private WorkoutFacade workoutFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void shouldGetWorkoutWithSetGroupsSuccessfully()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    final Workout workout = Instancio.create(Workout.class);

    when(workoutFacade.getWorkoutWithSetGroups(workout.getId())).thenReturn(workout);
    doNothing().when(authWorkoutsService).checkAccess(workout, AUTH_OPERATIONS);

    assertThat(getWorkoutSetGroupsService.execute(workout.getId())).isEqualTo(workout);
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToReadWorkout()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    final Workout workout = Instancio.create(Workout.class);
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(workoutFacade.getWorkoutWithSetGroups(workout.getId())).thenReturn(workout);
    doThrow(exception).when(authWorkoutsService).checkAccess(workout, AUTH_OPERATIONS);

    assertThatThrownBy(() -> getWorkoutSetGroupsService.execute(workout.getId()))
        .isEqualTo(exception);
  }

  @Test
  void shouldThrowEntityNotFoundExceptionWhenWorkoutIsNotFound() throws WorkoutNotFoundException {
    final UUID workoutId = UUID.randomUUID();
    final WorkoutNotFoundException exception =
        ExceptionGenerator.generateWorkoutNotFoundException();

    doThrow(exception).when(workoutFacade).getWorkoutWithSetGroups(workoutId);

    assertThatThrownBy(() -> getWorkoutSetGroupsService.execute(workoutId)).isEqualTo(exception);
  }
}
