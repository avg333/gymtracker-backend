package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.application;

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
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
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
class UpdateSetGroupDescriptionServiceImplTest {

  @InjectMocks private UpdateSetGroupDescriptionServiceImpl updateSetGroupDescriptionService;

  @Mock private SetGroupFacade setGroupFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void shouldUpdateDescriptionSuccessfully()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final String description = Instancio.create(String.class);
    final SetGroup updatedSetGroup = Instancio.create(SetGroup.class);

    final ArgumentCaptor<SetGroup> setGroupArgumentCaptor = ArgumentCaptor.forClass(SetGroup.class);

    when(setGroupFacade.getSetGroupWithWorkout(setGroup.getId())).thenReturn(setGroup);
    doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.UPDATE);
    when(setGroupFacade.saveSetGroup(setGroupArgumentCaptor.capture())).thenReturn(updatedSetGroup);

    assertThat(updateSetGroupDescriptionService.execute(setGroup.getId(), description))
        .isEqualTo(updatedSetGroup.getDescription());

    final SetGroup capturedSetGroup = setGroupArgumentCaptor.getValue();
    assertThat(capturedSetGroup.getDescription()).isEqualTo(description);

    verify(setGroupFacade).saveSetGroup(capturedSetGroup);
  }

  @Test
  void shouldNotUpdateDescriptionWhenSameAsBefore()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final String description = setGroup.getDescription();

    when(setGroupFacade.getSetGroupWithWorkout(setGroup.getId())).thenReturn(setGroup);
    doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.UPDATE);

    assertThat(updateSetGroupDescriptionService.execute(setGroup.getId(), description))
        .isEqualTo(description);

    verify(setGroupFacade, never()).saveSetGroup(any());
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToUpdateSetGroup()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    final String description = Instancio.create(String.class);
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(setGroupFacade.getSetGroupWithWorkout(setGroup.getId())).thenReturn(setGroup);
    doThrow(exception).when(authWorkoutsService).checkAccess(setGroup, AuthOperations.UPDATE);

    assertThatThrownBy(
            () -> updateSetGroupDescriptionService.execute(setGroup.getId(), description))
        .isEqualTo(exception);
  }

  @Test
  void shouldThrowSetGroupNotFoundExceptionWhenSetGroupIsNotFound()
      throws SetGroupNotFoundException {
    final UUID setGroupId = UUID.randomUUID();
    final String description = Instancio.create(String.class);
    final SetGroupNotFoundException exception =
        ExceptionGenerator.generateSetGroupNotFoundException();

    doThrow(exception).when(setGroupFacade).getSetGroupWithWorkout(setGroupId);

    assertThatThrownBy(() -> updateSetGroupDescriptionService.execute(setGroupId, description))
        .isEqualTo(exception);
  }
}
