package org.avillar.gymtracker.workoutapi.common.facade.exercise;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.exercisesapi.exercise.checkexercisereadaccess.application.CheckExerciseAccessService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.GetExercisesByIdsService;
import org.avillar.gymtracker.exercisesapi.exercise.modifyexerciseuses.application.ModifyExerciseUsesService;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException.AccessError;
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
class ExercisesFacadeImplTest {

  @InjectMocks private ExercisesFacadeImpl exercisesFacade;

  @Mock private CheckExerciseAccessService checkExerciseAccessService;
  @Mock private GetExercisesByIdsService getExercisesByIdsService;
  @Mock private ModifyExerciseUsesService modifyExerciseUsesService;

  @Test
  void shouldCheckExerciseAccessByIdSuccessfully()
      throws ExerciseIllegalAccessException, ExerciseNotFoundException {
    final UUID exerciseId = UUID.randomUUID();

    doNothing().when(checkExerciseAccessService).execute(exerciseId, AuthOperations.READ);

    assertDoesNotThrow(() -> exercisesFacade.checkExerciseAccessById(exerciseId));
  }

  @Test
  void shouldThrowExerciseUnavailableExceptionWhenExerciseIsNotFound()
      throws ExerciseIllegalAccessException, ExerciseNotFoundException {
    final UUID exerciseId = UUID.randomUUID();
    final ExerciseNotFoundException sourceException = new ExerciseNotFoundException(exerciseId);
    final ExerciseUnavailableException exception =
        new ExerciseUnavailableException(exerciseId, AccessError.NOT_FOUND);

    doThrow(sourceException)
        .when(checkExerciseAccessService)
        .execute(exerciseId, AuthOperations.READ);

    assertThat(
            assertThrows(
                ExerciseUnavailableException.class,
                () -> exercisesFacade.checkExerciseAccessById(exerciseId)))
        .isEqualTo(exception);
  }

  @Test
  void shouldThrowExerciseUnavailableExceptionWhenUserHasNoAccess()
      throws ExerciseIllegalAccessException, ExerciseNotFoundException {
    final UUID exerciseId = UUID.randomUUID();
    final ExerciseIllegalAccessException sourceException =
        new ExerciseIllegalAccessException(exerciseId, AuthOperations.READ, UUID.randomUUID());
    final ExerciseUnavailableException exception =
        new ExerciseUnavailableException(exerciseId, AccessError.NOT_ACCESS);

    doThrow(sourceException)
        .when(checkExerciseAccessService)
        .execute(exerciseId, AuthOperations.READ);

    assertThat(
            assertThrows(
                ExerciseUnavailableException.class,
                () -> exercisesFacade.checkExerciseAccessById(exerciseId)))
        .isEqualTo(exception);
  }

  @Test
  void shouldGetExercisesByIdsSuccessfully()
      throws IllegalAccessException, ExerciseUnavailableException {
    final Set<UUID> exerciseIds = Instancio.createSet(UUID.class);
    final List<Exercise> exercises = Instancio.createList(Exercise.class);

    when(getExercisesByIdsService.execute(exerciseIds)).thenReturn(exercises);

    assertThat(exercisesFacade.getExerciseByIds(exerciseIds)).isEqualTo(exercises);
  }

  @Test
  void shouldThrowIllegalAccessExceptionWhenUserHasNoAccessGettingExercisesByIds()
      throws IllegalAccessException {
    final Set<UUID> exerciseIds = Instancio.createSet(UUID.class);
    final ExerciseIllegalAccessException sourceException =
        new ExerciseIllegalAccessException(
            UUID.randomUUID(), AuthOperations.READ, UUID.randomUUID());
    final ExerciseUnavailableException exception =
        new ExerciseUnavailableException(sourceException.getEntityId(), AccessError.NOT_ACCESS);

    doThrow(sourceException).when(getExercisesByIdsService).execute(exerciseIds);

    assertThat(
            assertThrows(
                ExerciseUnavailableException.class,
                () -> exercisesFacade.getExerciseByIds(exerciseIds)))
        .isEqualTo(exception);
  }
}
