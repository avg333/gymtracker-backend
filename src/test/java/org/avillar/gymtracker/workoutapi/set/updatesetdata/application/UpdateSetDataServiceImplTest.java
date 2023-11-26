package org.avillar.gymtracker.workoutapi.set.updatesetdata.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.set.SetFacade;
import org.avillar.gymtracker.workoutapi.common.utils.ExceptionGenerator;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.mapper.UpdateSetDataServiceMapper;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataRequest;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponse;
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
class UpdateSetDataServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.UPDATE;

  @InjectMocks private UpdateSetDataServiceImpl updateSetDataService;

  @Mock private SetFacade setFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Mock private UpdateSetDataServiceMapper updateSetDataServiceMapper;

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldUpdateSetWithDateDataSuccessfully(final boolean isCompletedInRequest)
      throws SetNotFoundException, WorkoutIllegalAccessException {
    final UpdateSetDataRequest updateSetDataRequest = Instancio.create(UpdateSetDataRequest.class);
    updateSetDataRequest.setCompleted(isCompletedInRequest);
    final Set set = Instancio.create(Set.class);
    final Date completedAt = set.getCompletedAt();
    final Set savedSet = Instancio.create(Set.class);
    final UpdateSetDataResponse updateSetDataResponse =
        Instancio.create(UpdateSetDataResponse.class);

    final ArgumentCaptor<Set> setArgumentCaptor = ArgumentCaptor.forClass(Set.class);

    when(setFacade.getSetFull(set.getId())).thenReturn(set);
    doNothing().when(authWorkoutsService).checkAccess(set, AUTH_OPERATIONS);
    when(setFacade.saveSet(setArgumentCaptor.capture())).thenReturn(savedSet);
    when(updateSetDataServiceMapper.map(savedSet)).thenReturn(updateSetDataResponse);

    assertThat(updateSetDataService.execute(set.getId(), updateSetDataRequest))
        .isEqualTo(updateSetDataResponse);

    final Set savedSetValue = setArgumentCaptor.getValue();
    assertThat(savedSetValue.getDescription()).isEqualTo(updateSetDataRequest.getDescription());
    assertThat(savedSetValue.getWeight()).isEqualTo(updateSetDataRequest.getWeight());
    assertThat(savedSetValue.getReps()).isEqualTo(updateSetDataRequest.getReps());
    assertThat(savedSetValue.getRir()).isEqualTo(updateSetDataRequest.getRir());
    if (isCompletedInRequest) {
      assertThat(savedSetValue.getCompletedAt()).isEqualTo(completedAt);
    } else {
      assertThat(savedSetValue.getCompletedAt()).isNull();
    }

    verify(setFacade).saveSet(savedSetValue);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldUpdateSetWithoutDateDataSuccessfully(final boolean isCompleted)
      throws SetNotFoundException, WorkoutIllegalAccessException {
    final UpdateSetDataRequest updateSetDataRequest = Instancio.create(UpdateSetDataRequest.class);
    updateSetDataRequest.setCompleted(isCompleted);
    final Set set = Instancio.create(Set.class);
    set.setCompletedAt(null);
    final Date completedAt = set.getCompletedAt();
    final Set savedSet = Instancio.create(Set.class);
    final UpdateSetDataResponse updateSetDataResponse =
        Instancio.create(UpdateSetDataResponse.class);

    final ArgumentCaptor<Set> setArgumentCaptor = ArgumentCaptor.forClass(Set.class);

    when(setFacade.getSetFull(set.getId())).thenReturn(set);
    doNothing().when(authWorkoutsService).checkAccess(set, AUTH_OPERATIONS);
    when(setFacade.saveSet(setArgumentCaptor.capture())).thenReturn(savedSet);
    when(updateSetDataServiceMapper.map(savedSet)).thenReturn(updateSetDataResponse);

    final Date now = new Date();
    assertThat(updateSetDataService.execute(set.getId(), updateSetDataRequest))
        .isEqualTo(updateSetDataResponse);

    final Set savedSetValue = setArgumentCaptor.getValue();
    assertThat(savedSetValue.getDescription()).isEqualTo(updateSetDataRequest.getDescription());
    assertThat(savedSetValue.getWeight()).isEqualTo(updateSetDataRequest.getWeight());
    assertThat(savedSetValue.getReps()).isEqualTo(updateSetDataRequest.getReps());
    assertThat(savedSetValue.getRir()).isEqualTo(updateSetDataRequest.getRir());
    if (isCompleted) {
      assertThat(savedSetValue.getCompletedAt())
          .isNotNull()
          .isNotEqualTo(completedAt)
          .isCloseTo(now, TimeUnit.SECONDS.toMillis(5));
    } else {
      assertThat(savedSetValue.getCompletedAt()).isNull();
    }

    verify(setFacade).saveSet(savedSetValue);
  }

  @Test
  void shouldNotUpdateSetDataWithSameData()
      throws SetNotFoundException, WorkoutIllegalAccessException {
    final Set set = Instancio.create(Set.class);
    final UpdateSetDataRequest updateSetDataRequest = Instancio.create(UpdateSetDataRequest.class);
    updateSetDataRequest.setDescription(set.getDescription());
    updateSetDataRequest.setWeight(set.getWeight());
    updateSetDataRequest.setRir(set.getRir());
    updateSetDataRequest.setReps(set.getReps());
    updateSetDataRequest.setCompleted(set.getCompletedAt() != null);
    final UpdateSetDataResponse updateSetDataResponse =
        Instancio.create(UpdateSetDataResponse.class);

    when(setFacade.getSetFull(set.getId())).thenReturn(set);
    doNothing().when(authWorkoutsService).checkAccess(set, AUTH_OPERATIONS);
    when(updateSetDataServiceMapper.map(set)).thenReturn(updateSetDataResponse);

    assertThat(updateSetDataService.execute(set.getId(), updateSetDataRequest))
        .isEqualTo(updateSetDataResponse);

    verify(setFacade, never()).saveSet(any());
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToUpdateSetData()
      throws SetNotFoundException, WorkoutIllegalAccessException {
    final Set set = Instancio.create(Set.class);
    final UpdateSetDataRequest updateSetDataRequest = Instancio.create(UpdateSetDataRequest.class);
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(setFacade.getSetFull(set.getId())).thenReturn(set);
    doThrow(exception).when(authWorkoutsService).checkAccess(set, AUTH_OPERATIONS);

    assertThatThrownBy(() -> updateSetDataService.execute(set.getId(), updateSetDataRequest))
        .isEqualTo(exception);

    verify(setFacade, never()).saveSet(any());
  }

  @Test
  void shouldThrowSetNotFoundExceptionWhenSetNotFound() throws SetNotFoundException {
    final UUID setId = UUID.randomUUID();
    final UpdateSetDataRequest updateSetDataRequest = Instancio.create(UpdateSetDataRequest.class);
    final SetNotFoundException exception = ExceptionGenerator.generateSetNotFoundException();

    doThrow(exception).when(setFacade).getSetFull(setId);

    assertThatThrownBy(() -> updateSetDataService.execute(setId, updateSetDataRequest))
        .isEqualTo(exception);

    verify(setFacade, never()).saveSet(any());
  }
}
