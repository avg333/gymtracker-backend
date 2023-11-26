package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
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
class GetWorkoutsDateAndIdServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.READ;

  @InjectMocks private GetWorkoutsDateAndIdServiceImpl getWorkoutIdAndDateService;

  @Mock private WorkoutFacade workoutFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void shouldGetWorkoutsIdAndDateSuccessfully() throws WorkoutIllegalAccessException {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = null;
    final Map<LocalDate, UUID> daoResponse = Instancio.createMap(LocalDate.class, UUID.class);

    final ArgumentCaptor<Workout> workoutArgumentCaptor = ArgumentCaptor.forClass(Workout.class);

    doNothing()
        .when(authWorkoutsService)
        .checkAccess(workoutArgumentCaptor.capture(), eq(AUTH_OPERATIONS));
    when(workoutFacade.getWorkoutsIdAndDatesByUser(userId)).thenReturn(daoResponse);

    assertThat(getWorkoutIdAndDateService.execute(userId, exerciseId)).isEqualTo(daoResponse);

    final Workout workout = workoutArgumentCaptor.getValue();
    assertThat(workout).isNotNull();
    assertThat(workout.getUserId()).isEqualTo(userId);
  }

  @Test
  void shouldGetWorkoutsIdAndDateFilteringByExerciseSuccessfully()
      throws WorkoutIllegalAccessException {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final Map<LocalDate, UUID> daoResponse = Instancio.createMap(LocalDate.class, UUID.class);

    final ArgumentCaptor<Workout> workoutArgumentCaptor = ArgumentCaptor.forClass(Workout.class);

    doNothing()
        .when(authWorkoutsService)
        .checkAccess(workoutArgumentCaptor.capture(), eq(AUTH_OPERATIONS));
    when(workoutFacade.getWorkoutsIdAndDatesByUserAndExercise(userId, exerciseId))
        .thenReturn(daoResponse);

    assertThat(getWorkoutIdAndDateService.execute(userId, exerciseId)).isEqualTo(daoResponse);

    final Workout workout = workoutArgumentCaptor.getValue();
    assertThat(workout).isNotNull();
    assertThat(workout.getUserId()).isEqualTo(userId);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToGetWorkoutsDateAndId(
      final boolean isExerciseIdNull) throws WorkoutIllegalAccessException {
    final UUID exerciseId = isExerciseIdNull ? null : UUID.randomUUID();
    final UUID userId = UUID.randomUUID();
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();
    final Workout dummyWorkout = Workout.builder().userId(userId).build(); // FIXME

    doThrow(exception)
        .when(authWorkoutsService)
        .checkAccess(any(Workout.class), eq(AUTH_OPERATIONS));

    assertThatThrownBy(() -> getWorkoutIdAndDateService.execute(userId, exerciseId))
        .isEqualTo(exception);
  }
}
