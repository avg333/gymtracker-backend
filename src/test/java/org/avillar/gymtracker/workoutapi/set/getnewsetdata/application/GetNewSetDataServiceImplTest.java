package org.avillar.gymtracker.workoutapi.set.getnewsetdata.application;

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
import org.avillar.gymtracker.workoutapi.common.facade.set.SetFacade;
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
class GetNewSetDataServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.READ;

  @InjectMocks private GetNewSetDataServiceImpl getNewSetDataService;

  @Mock private SetFacade setFacade;
  @Mock private SetGroupFacade setGroupFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void shouldRetrieveNewSetDataSuccessfully()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final SetGroup setGroup = Instancio.create(SetGroup.class);

    when(setGroupFacade.getSetGroupFull(setGroup.getId())).thenReturn(setGroup);
    doNothing().when(authWorkoutsService).checkAccess(setGroup, AUTH_OPERATIONS);

    assertThat(getNewSetDataService.execute(setGroup.getId())).isNotNull();
    // TODO
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToReadSet()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(setGroupFacade.getSetGroupFull(setGroup.getId())).thenReturn(setGroup);
    doThrow(exception).when(authWorkoutsService).checkAccess(setGroup, AUTH_OPERATIONS);

    assertThatThrownBy(() -> getNewSetDataService.execute(setGroup.getId())).isEqualTo(exception);
  }

  @Test
  void shouldThrowSetGroupNotFoundExceptionWhenSetGroupIsNotFound()
      throws SetGroupNotFoundException {
    final UUID setGroupId = UUID.randomUUID();
    final SetGroupNotFoundException exception =
        ExceptionGenerator.generateSetGroupNotFoundException();

    doThrow(exception).when(setGroupFacade).getSetGroupFull(setGroupId);

    assertThatThrownBy(() -> getNewSetDataService.execute(setGroupId)).isEqualTo(exception);
  }
}
