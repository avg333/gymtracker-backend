package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.setgroup.SetGroupFacade;
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
class GetSetGroupsByExerciseServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.READ;

  @InjectMocks private GetSetGroupsByExerciseServiceImpl getSetGroupsByExerciseService;

  @Mock private SetGroupFacade setGroupFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void shouldGetExerciseSetGroupsSuccessfully() throws WorkoutIllegalAccessException {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final List<SetGroup> setGroups = Instancio.createList(SetGroup.class);

    final ArgumentCaptor<SetGroup> setGroupArgumentCaptor = ArgumentCaptor.forClass(SetGroup.class);

    doNothing()
        .when(authWorkoutsService)
        .checkAccess(setGroupArgumentCaptor.capture(), eq(AUTH_OPERATIONS));
    when(setGroupFacade.getSetGroupsFullByUserIdAndExerciseId(userId, exerciseId))
        .thenReturn(setGroups);

    assertThat(getSetGroupsByExerciseService.execute(userId, exerciseId)).isEqualTo(setGroups);

    final SetGroup setGroup = setGroupArgumentCaptor.getValue();
    assertThat(setGroup).isNotNull();
    assertThat(setGroup.getWorkout().getUserId()).isEqualTo(userId);
  }

  @Test
  void shouldGetExerciseSetGroupsSuccessfullyWithoutResults() throws WorkoutIllegalAccessException {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final List<SetGroup> setGroups = Collections.emptyList();

    final ArgumentCaptor<SetGroup> setGroupArgumentCaptor = ArgumentCaptor.forClass(SetGroup.class);

    doNothing()
        .when(authWorkoutsService)
        .checkAccess(setGroupArgumentCaptor.capture(), eq(AUTH_OPERATIONS));
    when(setGroupFacade.getSetGroupsFullByUserIdAndExerciseId(userId, exerciseId))
        .thenReturn(setGroups);

    assertThat(getSetGroupsByExerciseService.execute(userId, exerciseId)).isEqualTo(setGroups);

    final SetGroup setGroup = setGroupArgumentCaptor.getValue();
    assertThat(setGroup).isNotNull();
    assertThat(setGroup.getWorkout().getUserId()).isEqualTo(userId);
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToReadSet()
      throws WorkoutIllegalAccessException {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final SetGroup dummySetGroup = // FIXME
        SetGroup.builder().workout(Workout.builder().userId(userId).build()).build();
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    doThrow(exception)
        .when(authWorkoutsService)
        .checkAccess(any(SetGroup.class), eq(AUTH_OPERATIONS));

    assertThatThrownBy(() -> getSetGroupsByExerciseService.execute(userId, exerciseId))
        .isEqualTo(exception);
  }
}
