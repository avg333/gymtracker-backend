package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.common.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.ExerciseFacade;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.GetExercisesFilter;
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
class GetExercisesByFilterServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.READ;

  @InjectMocks private GetExercisesByFilterServiceImpl getExercisesByFilterServiceImpl;

  @Mock private ExerciseFacade exerciseFacade;
  @Mock private AuthExercisesService authExercisesService;

  @Test
  void shouldGetExercisesByFilterSuccessfully() throws ExerciseIllegalAccessException {
    final GetExercisesFilter getExercisesFilter = Instancio.create(GetExercisesFilter.class);
    final UUID userId = Instancio.create(UUID.class);
    final List<Exercise> exercises = Instancio.createList(Exercise.class);

    when(authExercisesService.getLoggedUserId()).thenReturn(userId);
    when(exerciseFacade.getExercisesByFilter(userId, getExercisesFilter)).thenReturn(exercises);
    doNothing().when(authExercisesService).checkAccess(exercises, AUTH_OPERATIONS);

    assertThat(getExercisesByFilterServiceImpl.execute(getExercisesFilter)).isEqualTo(exercises);
  }

  @Test
  void shouldThrowExerciseIllegalAccessExceptionWhenUserHasNoPermissionToReadExercise()
      throws ExerciseIllegalAccessException {
    final GetExercisesFilter getExercisesFilter = Instancio.create(GetExercisesFilter.class);
    final UUID userId = Instancio.create(UUID.class);
    final List<Exercise> exercises = Instancio.createList(Exercise.class);
    final ExerciseIllegalAccessException exception =
        new ExerciseIllegalAccessException(exercises.get(0).getId(), AUTH_OPERATIONS, userId);

    when(authExercisesService.getLoggedUserId()).thenReturn(userId);
    when(exerciseFacade.getExercisesByFilter(userId, getExercisesFilter)).thenReturn(exercises);
    doThrow(exception).when(authExercisesService).checkAccess(exercises, AUTH_OPERATIONS);

    assertThatThrownBy(() -> getExercisesByFilterServiceImpl.execute(getExercisesFilter))
        .isEqualTo(exception);
  }
}
