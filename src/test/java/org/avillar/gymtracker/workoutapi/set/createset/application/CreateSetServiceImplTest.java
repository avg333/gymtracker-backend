package org.avillar.gymtracker.workoutapi.set.createset.application;

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
import org.avillar.gymtracker.workoutapi.common.domain.Set;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class CreateSetServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.CREATE;

  @InjectMocks private CreateSetServiceImpl createSetService;

  @Mock private SetFacade setFacade;
  @Mock private SetGroupFacade setGroupFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void shouldCreateSetSuccessfully()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final Set set = Instancio.create(Set.class);
    final Set setAfterSave = Instancio.create(Set.class);

    final ArgumentCaptor<Set> setCaptorBeforeAuth = ArgumentCaptor.forClass(Set.class);

    when(setGroupFacade.getSetGroupFull(setGroup.getId())).thenReturn(setGroup);
    doNothing()
        .when(authWorkoutsService)
        .checkAccess(setCaptorBeforeAuth.capture(), eq(AUTH_OPERATIONS));
    when(setFacade.saveSet(set)).thenReturn(setAfterSave);

    assertThat(createSetService.execute(setGroup.getId(), set)).isEqualTo(setAfterSave);

    final Set setCaptorBeforeAuthValue = setCaptorBeforeAuth.getValue();
    assertThat(setCaptorBeforeAuthValue)
        .isNotNull()
        .usingRecursiveComparison()
        .ignoringFields("setGroup", "listOrder")
        .isEqualTo(set);
    assertThat(setCaptorBeforeAuthValue.getSetGroup()).isEqualTo(setGroup);
    assertThat(setCaptorBeforeAuthValue.getListOrder()).isEqualTo(setGroup.getSets().size());

    verify(setFacade).saveSet(set);
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToCreateSet()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final Set set = Instancio.create(Set.class);
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(setGroupFacade.getSetGroupFull(setGroup.getId())).thenReturn(setGroup);
    // TODO Check if set has been set with setGroup before auth
    doThrow(exception).when(authWorkoutsService).checkAccess(set, AUTH_OPERATIONS);

    assertThatThrownBy(() -> createSetService.execute(setGroup.getId(), set)).isEqualTo(exception);

    verify(setFacade, never()).saveSet(any());
  }

  @Test
  void shouldThrowSetGroupNotFoundExceptionWhenSetGroupIsNotFound()
      throws SetGroupNotFoundException {
    final UUID setGroupId = UUID.randomUUID();
    final Set set = Instancio.create(Set.class);
    final SetGroupNotFoundException exception =
        ExceptionGenerator.generateSetGroupNotFoundException();

    doThrow(exception).when(setGroupFacade).getSetGroupFull(setGroupId);

    assertThatThrownBy(() -> createSetService.execute(setGroupId, set)).isEqualTo(exception);

    verify(setFacade, never()).saveSet(any());
  }
}
