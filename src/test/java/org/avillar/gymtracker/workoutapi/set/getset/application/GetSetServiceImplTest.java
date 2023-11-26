package org.avillar.gymtracker.workoutapi.set.getset.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.set.SetFacade;
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
class GetSetServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.READ;

  @InjectMocks private GetSetServiceImpl getSetService;

  @Mock private SetFacade setFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void shouldGetSetSuccessfully() throws SetNotFoundException, WorkoutIllegalAccessException {
    final Set set = Instancio.create(Set.class);

    when(setFacade.getSetFull(set.getId())).thenReturn(set);
    doNothing().when(authWorkoutsService).checkAccess(set, AUTH_OPERATIONS);

    assertThat(getSetService.execute(set.getId())).isEqualTo(set);
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToReadSet()
      throws SetNotFoundException, WorkoutIllegalAccessException {
    final Set set = Instancio.create(Set.class);
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(setFacade.getSetFull(set.getId())).thenReturn(set);
    doThrow(exception).when(authWorkoutsService).checkAccess(set, AUTH_OPERATIONS);

    assertThatThrownBy(() -> getSetService.execute(set.getId())).isEqualTo(exception);
  }

  @Test
  void shouldThrowSetNotFoundExceptionWhenSetIsNotFound() throws SetNotFoundException {
    final UUID setId = UUID.randomUUID();
    final SetNotFoundException exception = ExceptionGenerator.generateSetNotFoundException();

    doThrow(exception).when(setFacade).getSetFull(setId);

    assertThatThrownBy(() -> getSetService.execute(setId)).isEqualTo(exception);
  }
}
