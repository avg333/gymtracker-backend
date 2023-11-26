package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application;

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
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.exercise.ExercisesFacade;
import org.avillar.gymtracker.workoutapi.common.facade.setgroup.SetGroupFacade;
import org.avillar.gymtracker.workoutapi.common.facade.workout.WorkoutFacade;
import org.avillar.gymtracker.workoutapi.common.utils.ExceptionGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class CreateSetGroupServiceImplTest {

  // TODO Repasar

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.CREATE;

  @InjectMocks private CreateSetGroupServiceImpl createSetGroupService;

  @Mock private SetGroupFacade setGroupFacade;
  @Mock private WorkoutFacade workoutFacade;
  @Mock private ExercisesFacade exercisesFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldCreateSetGroupSuccessfully(final boolean increaseExerciseUsesError)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException, ExerciseUnavailableException {
    final Workout workout = Instancio.create(Workout.class);
    final SetGroup mappedSetGroup = Instancio.create(SetGroup.class);
    final SetGroup savedSetGroup = Instancio.create(SetGroup.class);

    final ArgumentCaptor<SetGroup> setGroupCaptorBeforeAuth =
        ArgumentCaptor.forClass(SetGroup.class);
    final ArgumentCaptor<SetGroup> setGroupCaptorBeforeSave =
        ArgumentCaptor.forClass(SetGroup.class);

    when(workoutFacade.getWorkoutWithSetGroups(workout.getId())).thenReturn(workout);
    doNothing()
        .when(authWorkoutsService)
        .checkAccess(setGroupCaptorBeforeAuth.capture(), eq(AUTH_OPERATIONS));
    doNothing().when(exercisesFacade).checkExerciseAccessById(mappedSetGroup.getExerciseId());
    if (increaseExerciseUsesError) {
      doThrow(ExceptionGenerator.generateExerciseUnavailableException())
          .when(exercisesFacade)
          .incrementExerciseUses(workout.getUserId(), mappedSetGroup.getExerciseId());
    } else {
      when(exercisesFacade.incrementExerciseUses(
              workout.getUserId(), mappedSetGroup.getExerciseId()))
          .thenReturn(Instancio.create(Integer.class));
    }
    when(setGroupFacade.saveSetGroup(setGroupCaptorBeforeSave.capture())).thenReturn(savedSetGroup);

    assertThat(createSetGroupService.execute(workout.getId(), mappedSetGroup))
        .isEqualTo(savedSetGroup);

    final SetGroup setGroupCaptorBeforeAuthValue = setGroupCaptorBeforeAuth.getValue();
    assertThat(setGroupCaptorBeforeAuthValue)
        .isNotNull()
        .usingRecursiveComparison()
        .ignoringFields("workout", "listOrder")
        .isEqualTo(mappedSetGroup);
    assertThat(setGroupCaptorBeforeAuthValue.getWorkout()).isEqualTo(workout);
    assertThat(setGroupCaptorBeforeAuthValue.getListOrder())
        .isEqualTo(workout.getSetGroups().size());

    final SetGroup setGroupCaptorBeforeSaveValue = setGroupCaptorBeforeSave.getValue();
    assertThat(setGroupCaptorBeforeSaveValue).isEqualTo(setGroupCaptorBeforeAuthValue);

    verify(exercisesFacade).checkExerciseAccessById(mappedSetGroup.getExerciseId());
    verify(exercisesFacade)
        .incrementExerciseUses(workout.getUserId(), mappedSetGroup.getExerciseId());
    verify(setGroupFacade).saveSetGroup(setGroupCaptorBeforeSaveValue);
  }

  @Test
  void shouldThrowExerciseUnavailableExceptionWhenUserHasNoPermissionToUseExerciseOrNotExists()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException, ExerciseUnavailableException {
    final Workout workout = Instancio.create(Workout.class);
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final ExerciseUnavailableException exception =
        ExceptionGenerator.generateExerciseUnavailableException();

    when(workoutFacade.getWorkoutWithSetGroups(workout.getId())).thenReturn(workout);
    doNothing().when(authWorkoutsService).checkAccess(any(SetGroup.class), eq(AUTH_OPERATIONS));
    doThrow(exception).when(exercisesFacade).checkExerciseAccessById(setGroup.getExerciseId());

    assertThatThrownBy(() -> createSetGroupService.execute(workout.getId(), setGroup))
        .isEqualTo(exception);

    verify(exercisesFacade).checkExerciseAccessById(setGroup.getExerciseId());
    verify(exercisesFacade, never()).incrementExerciseUses(any(), any());
    verify(setGroupFacade, never()).saveSetGroup(any());
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToCreateSetGroupInWorkout()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException, ExerciseUnavailableException {
    final Workout workout = Instancio.create(Workout.class);
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(workoutFacade.getWorkoutWithSetGroups(workout.getId())).thenReturn(workout);
    doThrow(exception).when(authWorkoutsService).checkAccess(setGroup, AUTH_OPERATIONS);

    assertThatThrownBy(() -> createSetGroupService.execute(workout.getId(), setGroup))
        .isEqualTo(exception);

    verify(exercisesFacade, never()).checkExerciseAccessById(any());
    verify(exercisesFacade, never()).incrementExerciseUses(any(), any());
    verify(setGroupFacade, never()).saveSetGroup(any());
  }

  @Test
  void shouldThrowWorkoutNotFoundExceptionWhenWorkoutIsNotFound()
      throws WorkoutNotFoundException, ExerciseUnavailableException {
    final UUID workoutId = UUID.randomUUID();
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final WorkoutNotFoundException exception =
        ExceptionGenerator.generateWorkoutNotFoundException();

    doThrow(exception).when(workoutFacade).getWorkoutWithSetGroups(workoutId);

    assertThatThrownBy(() -> createSetGroupService.execute(workoutId, setGroup))
        .isEqualTo(exception);

    verify(exercisesFacade, never()).checkExerciseAccessById(any());
    verify(exercisesFacade, never()).incrementExerciseUses(any(), any());
    verify(setGroupFacade, never()).saveSetGroup(any());
  }
}
