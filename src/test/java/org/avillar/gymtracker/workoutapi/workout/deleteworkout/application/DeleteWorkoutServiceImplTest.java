package org.avillar.gymtracker.workoutapi.workout.deleteworkout.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.exercise.ExercisesFacade;
import org.avillar.gymtracker.workoutapi.common.facade.exercise.model.DeleteExerciseUsesRequestFacade;
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
class DeleteWorkoutServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.DELETE;

  @InjectMocks private DeleteWorkoutServiceImpl deleteWorkoutService;

  @Mock private WorkoutFacade workoutFacade;
  @Mock private ExercisesFacade exercisesFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void shouldDeleteWorkoutSuccessfully()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException, ExerciseUnavailableException {
    final Workout workout = Instancio.create(Workout.class);
    final ArgumentCaptor<DeleteExerciseUsesRequestFacade> decrementExercisesUsesCaptor =
        ArgumentCaptor.forClass(DeleteExerciseUsesRequestFacade.class);

    when(workoutFacade.getWorkoutWithSetGroups(workout.getId())).thenReturn(workout);
    doNothing().when(authWorkoutsService).checkAccess(workout, AUTH_OPERATIONS);
    doNothing()
        .when(exercisesFacade)
        .decrementExercisesUses(eq(workout.getUserId()), decrementExercisesUsesCaptor.capture());

    assertDoesNotThrow(() -> deleteWorkoutService.execute(workout.getId()));

    final List<UUID> exerciseIds =
        workout.getSetGroups().stream()
            .map(SetGroup::getExerciseId)
            .filter(Objects::nonNull)
            .toList();
    final DeleteExerciseUsesRequestFacade captorValue = decrementExercisesUsesCaptor.getValue();
    assertThat(captorValue).isNotNull();
    assertThat(captorValue.getExerciseUses()).isNotNull().hasSameSizeAs(exerciseIds);
    captorValue
        .getExerciseUses()
        .forEach(
            exerciseUses -> {
              assertThat(exerciseUses.getUses()).isEqualTo(1);
              assertThat(exerciseIds).contains(exerciseUses.getExerciseId());
            });

    verify(workoutFacade).deleteWorkout(workout.getId());
    verify(exercisesFacade).decrementExercisesUses(workout.getUserId(), captorValue);
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToDeleteWorkout()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException, ExerciseUnavailableException {
    final Workout workout = Instancio.create(Workout.class);
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(workoutFacade.getWorkoutWithSetGroups(workout.getId())).thenReturn(workout);
    doThrow(exception).when(authWorkoutsService).checkAccess(workout, AUTH_OPERATIONS);

    assertThatThrownBy(() -> deleteWorkoutService.execute(workout.getId())).isEqualTo(exception);

    verify(workoutFacade, never()).deleteWorkout(any());
    verify(exercisesFacade, never()).decrementExercisesUses(any(), any());
  }

  @Test
  void shouldThrowEntityNotFoundExceptionWhenGettingNonExistentWorkout()
      throws WorkoutNotFoundException, ExerciseUnavailableException {
    final UUID workoutId = UUID.randomUUID();
    final WorkoutNotFoundException exception =
        ExceptionGenerator.generateWorkoutNotFoundException();

    doThrow(exception).when(workoutFacade).getWorkoutWithSetGroups(workoutId);

    assertThatThrownBy(() -> deleteWorkoutService.execute(workoutId)).isEqualTo(exception);

    verify(workoutFacade, never()).deleteWorkout(any());
    verify(exercisesFacade, never()).decrementExercisesUses(any(), any());
  }
}
