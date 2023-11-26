package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
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
class GetExercisesByIdsServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.READ;

  @InjectMocks private GetExercisesByIdsServiceImpl getExercisesByIdsServiceImpl;

  @Mock private ExerciseFacade exerciseFacade;
  @Mock private AuthExercisesService authExercisesService;

  @Test
  void shouldGetExercisesByIdsSuccessfully() throws IllegalAccessException {
    final List<UUID> exerciseIds = Instancio.createList(UUID.class);
    final List<Exercise> exercises = Instancio.createList(Exercise.class);

    when(exerciseFacade.getExercisesByIds(exerciseIds)).thenReturn(exercises);
    doNothing().when(authExercisesService).checkAccess(exercises, AUTH_OPERATIONS);

    assertThat(getExercisesByIdsServiceImpl.execute(exerciseIds)).isEqualTo(exercises);
  }

  @Test
  void shouldThrowExerciseIllegalAccessExceptionWhenUserHasNoPermissionToReadExercise()
      throws ExerciseIllegalAccessException {
    final UUID userId = UUID.randomUUID();
    final List<UUID> exerciseIds = Instancio.createList(UUID.class);
    final List<Exercise> exercises = Instancio.createList(Exercise.class);
    final ExerciseIllegalAccessException exception =
        new ExerciseIllegalAccessException(exerciseIds.get(0), AUTH_OPERATIONS, userId);

    when(exerciseFacade.getExercisesByIds(exerciseIds)).thenReturn(exercises);
    doThrow(exception).when(authExercisesService).checkAccess(exercises, AUTH_OPERATIONS);

    assertThatThrownBy(() -> getExercisesByIdsServiceImpl.execute(exerciseIds))
        .isEqualTo(exception);
  }
}
