package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.common.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.ExerciseFacade;
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
class GetExerciseByIdServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.READ;

  @InjectMocks private GetExerciseByIdServiceImpl getExerciseByIdServiceImpl;

  @Mock private ExerciseFacade exerciseFacade;
  @Mock private AuthExercisesService authExercisesService;

  @Test
  void shouldGetExerciseByIdSuccessfully()
      throws ExerciseNotFoundException, ExerciseIllegalAccessException {
    final UUID exerciseId = UUID.randomUUID();
    final Exercise exercise = Instancio.create(Exercise.class);

    when(exerciseFacade.getFullExerciseById(exerciseId)).thenReturn(exercise);
    doNothing().when(authExercisesService).checkAccess(exercise, AUTH_OPERATIONS);

    assertThat(getExerciseByIdServiceImpl.execute(exerciseId)).isEqualTo(exercise);
  }

  @Test
  void shouldThrowExerciseIllegalAccessExceptionWhenUserHasNoPermissionToReadExercise()
      throws ExerciseNotFoundException, ExerciseIllegalAccessException {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final Exercise exercise = Instancio.create(Exercise.class);
    final ExerciseIllegalAccessException exception =
        new ExerciseIllegalAccessException(exerciseId, AUTH_OPERATIONS, userId);

    when(exerciseFacade.getFullExerciseById(exerciseId)).thenReturn(exercise);
    doThrow(exception).when(authExercisesService).checkAccess(exercise, AUTH_OPERATIONS);

    assertThatThrownBy(() -> getExerciseByIdServiceImpl.execute(exerciseId)).isEqualTo(exception);
  }

  @Test
  void shouldThrowExerciseNotFoundExceptionWhenUserHasNoPermissionToReadExercise()
      throws ExerciseNotFoundException {
    final UUID exerciseId = UUID.randomUUID();
    final ExerciseNotFoundException exception = new ExerciseNotFoundException(exerciseId);

    when(exerciseFacade.getFullExerciseById(exerciseId)).thenThrow(exception);

    assertThatThrownBy(() -> getExerciseByIdServiceImpl.execute(exerciseId)).isEqualTo(exception);
  }
}
