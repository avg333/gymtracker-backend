package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.mapper.GetExerciseByIdServiceMapperImpl;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.model.GetExerciseByIdResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetExerciseByIdServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetExerciseByIdServiceImpl getExerciseByIdService;

  @Mock private ExerciseDao exerciseDao;
  @Mock private AuthExercisesService authExercisesService;
  @Spy private GetExerciseByIdServiceMapperImpl getExerciseByIdServiceMapper;

  @Test
  void getOk() {
    final Exercise expected = easyRandom.nextObject(Exercise.class);
    final UUID exerciseId = expected.getId();

    when(exerciseDao.getFullExerciseByIds(Set.of(exerciseId))).thenReturn(List.of(expected));
    doNothing().when(authExercisesService).checkAccess(expected, AuthOperations.READ);

    final GetExerciseByIdResponseApplication result = getExerciseByIdService.execute(exerciseId);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void getNotPermission() {
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    final UUID exerciseId = exercise.getId();
    final AuthOperations authOperation = AuthOperations.READ;
    final UUID userId = UUID.randomUUID();
    exercise.setAccessType(AccessTypeEnum.PRIVATE);
    exercise.setOwner(UUID.randomUUID());

    when(exerciseDao.getFullExerciseByIds(Set.of(exerciseId))).thenReturn(List.of(exercise));
    doThrow(new IllegalAccessException(exercise, authOperation, userId))
        .when(authExercisesService)
        .checkAccess(exercise, authOperation);

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class, () -> getExerciseByIdService.execute(exerciseId));
    assertEquals(Exercise.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(exercise.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperation, exception.getAuthOperations());
  }

  @Test
  void getNotFound() {
    final UUID exerciseId = UUID.randomUUID();

    when(exerciseDao.getFullExerciseByIds(Set.of(exerciseId))).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class, () -> getExerciseByIdService.execute(exerciseId));
    assertEquals(Exercise.class.getSimpleName(), exception.getClassName());
    assertEquals(exerciseId, exception.getId());
  }
}
