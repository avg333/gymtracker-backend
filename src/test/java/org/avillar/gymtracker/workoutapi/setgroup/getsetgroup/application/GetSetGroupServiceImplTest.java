package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.setgroup.SetGroupFacade;
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
class GetSetGroupServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.READ;

  @InjectMocks private GetSetGroupServiceImpl getSetGroupService;

  @Mock private SetGroupFacade setGroupFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void shouldGetSetSetGroupSuccessfully()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final SetGroup setGroup = Instancio.create(SetGroup.class);

    when(setGroupFacade.getSetGroupWithWorkout(setGroup.getId())).thenReturn(setGroup);
    doNothing().when(authWorkoutsService).checkAccess(setGroup, AUTH_OPERATIONS);

    assertThat(getSetGroupService.execute(setGroup.getId())).isEqualTo(setGroup);
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToReadSetGroup()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(setGroupFacade.getSetGroupWithWorkout(setGroup.getId())).thenReturn(setGroup);
    doThrow(exception).when(authWorkoutsService).checkAccess(setGroup, AUTH_OPERATIONS);

    assertThatThrownBy(() -> getSetGroupService.execute(setGroup.getId())).isEqualTo(exception);
  }

  @Test
  void shouldThrowWorkoutNotFoundExceptionWhenSetGroupIsNotFound()
      throws SetGroupNotFoundException {
    final UUID setGroupId = UUID.randomUUID();
    final SetGroupNotFoundException exception =
        ExceptionGenerator.generateSetGroupNotFoundException();

    doThrow(exception).when(setGroupFacade).getSetGroupWithWorkout(setGroupId);

    assertThatThrownBy(() -> getSetGroupService.execute(setGroupId)).isEqualTo(exception);
  }
}
