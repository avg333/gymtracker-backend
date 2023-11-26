package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.application;

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
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.exercise.ExercisesFacade;
import org.avillar.gymtracker.workoutapi.common.facade.setgroup.SetGroupFacade;
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
class UpdateSetGroupExerciseServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.UPDATE;

  @InjectMocks private UpdateSetGroupExerciseServiceImpl updateSetGroupExerciseService;

  @Mock private SetGroupFacade setGroupFacade;
  @Mock private ExercisesFacade exercisesFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldUpdateExerciseIdSuccessfully(final boolean modifyExerciseUsesError)
      throws SetGroupNotFoundException,
          WorkoutIllegalAccessException,
          ExerciseUnavailableException {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final UUID exerciseId = UUID.randomUUID();
    final UUID oldExerciseId = setGroup.getExerciseId();
    final SetGroup updatedSetGroup = Instancio.create(SetGroup.class);

    final ArgumentCaptor<SetGroup> setGroupArgumentCaptor = ArgumentCaptor.forClass(SetGroup.class);

    when(setGroupFacade.getSetGroupWithWorkout(setGroup.getId())).thenReturn(setGroup);
    doNothing().when(authWorkoutsService).checkAccess(setGroup, AUTH_OPERATIONS);
    doNothing().when(exercisesFacade).checkExerciseAccessById(exerciseId);
    if (modifyExerciseUsesError) {
      doThrow(ExceptionGenerator.generateExerciseUnavailableException())
          .when(exercisesFacade)
          .swapExerciseUses(setGroup.getWorkout().getUserId(), exerciseId, oldExerciseId);
    } else {
      when(exercisesFacade.swapExerciseUses(
              setGroup.getWorkout().getUserId(), exerciseId, oldExerciseId))
          .thenReturn(Instancio.create(Integer.class));
    }
    when(setGroupFacade.saveSetGroup(setGroupArgumentCaptor.capture())).thenReturn(updatedSetGroup);

    assertThat(updateSetGroupExerciseService.execute(setGroup.getId(), exerciseId))
        .isEqualTo(updatedSetGroup.getExerciseId());

    final SetGroup capturedSetGroup = setGroupArgumentCaptor.getValue();
    assertThat(capturedSetGroup.getExerciseId()).isEqualTo(exerciseId);

    verify(exercisesFacade).checkExerciseAccessById(exerciseId);
    verify(exercisesFacade)
        .swapExerciseUses(setGroup.getWorkout().getUserId(), exerciseId, oldExerciseId);
    verify(setGroupFacade).saveSetGroup(capturedSetGroup);
  }

  @Test
  void shouldThrowExerciseUnavailableExceptionWhenGettingNonAccessingExerciseOrNotExists()
      throws SetGroupNotFoundException,
          WorkoutIllegalAccessException,
          ExerciseUnavailableException {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final UUID exerciseId = UUID.randomUUID();
    final ExerciseUnavailableException exception =
        ExceptionGenerator.generateExerciseUnavailableException();

    when(setGroupFacade.getSetGroupWithWorkout(setGroup.getId())).thenReturn(setGroup);
    doNothing().when(authWorkoutsService).checkAccess(setGroup, AUTH_OPERATIONS);
    doThrow(exception).when(exercisesFacade).checkExerciseAccessById(exerciseId);

    assertThatThrownBy(() -> updateSetGroupExerciseService.execute(setGroup.getId(), exerciseId))
        .isEqualTo(exception);

    verify(exercisesFacade).checkExerciseAccessById(exerciseId);
    verify(exercisesFacade, never()).swapExerciseUses(any(), any(), any());
    verify(setGroupFacade, never()).saveSetGroup(any());
  }

  @Test
  void shouldNotUpdateExerciseIdWhenSameAsBefore()
      throws SetGroupNotFoundException,
          WorkoutIllegalAccessException,
          ExerciseUnavailableException {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final UUID exerciseId = setGroup.getExerciseId();

    when(setGroupFacade.getSetGroupWithWorkout(setGroup.getId())).thenReturn(setGroup);
    doNothing().when(authWorkoutsService).checkAccess(setGroup, AUTH_OPERATIONS);

    assertThat(updateSetGroupExerciseService.execute(setGroup.getId(), exerciseId))
        .isEqualTo(exerciseId);

    verify(exercisesFacade, never()).swapExerciseUses(any(), any(), any());
    verify(exercisesFacade, never()).checkExerciseAccessById(any());
    verify(setGroupFacade, never()).saveSetGroup(any());
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToUpdateSetGroup()
      throws SetGroupNotFoundException,
          WorkoutIllegalAccessException,
          ExerciseUnavailableException {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final UUID exerciseId = UUID.randomUUID();
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(setGroupFacade.getSetGroupWithWorkout(setGroup.getId())).thenReturn(setGroup);
    doThrow(exception).when(authWorkoutsService).checkAccess(setGroup, AUTH_OPERATIONS);

    assertThatThrownBy(() -> updateSetGroupExerciseService.execute(setGroup.getId(), exerciseId))
        .isEqualTo(exception);

    verify(exercisesFacade, never()).swapExerciseUses(any(), any(), any());
    verify(exercisesFacade, never()).checkExerciseAccessById(any());
    verify(setGroupFacade, never()).saveSetGroup(any());
  }

  @Test
  void shouldThrowSetGroupNotFoundExceptionWhenSetGroupIsNotFound()
      throws SetGroupNotFoundException, ExerciseUnavailableException {
    final UUID setGroupId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final SetGroupNotFoundException exception =
        ExceptionGenerator.generateSetGroupNotFoundException();

    doThrow(exception).when(setGroupFacade).getSetGroupWithWorkout(setGroupId);

    assertThatThrownBy(() -> updateSetGroupExerciseService.execute(setGroupId, exerciseId))
        .isEqualTo(exception);

    verify(exercisesFacade, never()).swapExerciseUses(any(), any(), any());
    verify(exercisesFacade, never()).checkExerciseAccessById(any());
    verify(setGroupFacade, never()).saveSetGroup(any());
  }
}
