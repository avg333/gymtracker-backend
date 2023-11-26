package org.avillar.gymtracker.exercisesapi.exercise.checkexercisereadaccess.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class CheckExerciseAccessServiceImplTest {

  @InjectMocks private CheckExerciseAccessServiceImpl checkExerciseAccessServiceImpl;

  @Mock private ExerciseFacade exerciseFacade;
  @Mock private AuthExercisesService authExercisesService;

  @ParameterizedTest
  @EnumSource(AuthOperations.class)
  void shouldCheckAccessOkSuccessfully(final AuthOperations authOperations)
      throws ExerciseNotFoundException, ExerciseIllegalAccessException {
    final UUID exerciseId = UUID.randomUUID();
    final Exercise exercise = Instancio.create(Exercise.class);

    when(exerciseFacade.getExerciseById(exerciseId)).thenReturn(exercise);
    doNothing().when(authExercisesService).checkAccess(exercise, authOperations);

    assertDoesNotThrow(() -> checkExerciseAccessServiceImpl.execute(exerciseId, authOperations));
  }

  @ParameterizedTest
  @EnumSource(AuthOperations.class)
  void shouldThrowExerciseIllegalAccessExceptionWhenUserHasNoAccess(
      final AuthOperations authOperations)
      throws ExerciseNotFoundException, ExerciseIllegalAccessException {
    final UUID exerciseId = UUID.randomUUID();
    final Exercise exercise = Instancio.create(Exercise.class);
    final ExerciseIllegalAccessException exception =
        new ExerciseIllegalAccessException(exercise.getId(), authOperations, UUID.randomUUID());

    when(exerciseFacade.getExerciseById(exerciseId)).thenReturn(exercise);
    doThrow(exception).when(authExercisesService).checkAccess(exercise, authOperations);

    assertThatThrownBy(() -> checkExerciseAccessServiceImpl.execute(exerciseId, authOperations))
        .isEqualTo(exception);
  }

  @ParameterizedTest
  @EnumSource(AuthOperations.class)
  void shouldThrowExerciseNotFoundExceptionWhenExerciseIsNotFound(
      final AuthOperations authOperations) throws ExerciseNotFoundException {
    final UUID exerciseId = UUID.randomUUID();
    final ExerciseNotFoundException exception = new ExerciseNotFoundException(exerciseId);

    when(exerciseFacade.getExerciseById(exerciseId)).thenThrow(exception);

    assertThatThrownBy(() -> checkExerciseAccessServiceImpl.execute(exerciseId, authOperations))
        .isEqualTo(exception);
  }
}
