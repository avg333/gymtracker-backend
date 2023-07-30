package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.avillar.gymtracker.common.base.domain.BaseEntity;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.mapper.GetExercisesByIdsServiceMapper;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.model.GetExercisesByIdsResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class GetExercisesByIdsServiceImplTest {

  private static final int LIST_SIZE = 5;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetExercisesByIdsServiceImpl getExercisesByIdsService;

  @Mock private ExerciseDao exerciseDao;
  @Mock private AuthExercisesService authExercisesService;

  @Spy
  private final GetExercisesByIdsServiceMapper getExercisesByIdsServiceMapper =
      Mappers.getMapper(GetExercisesByIdsServiceMapper.class);

  @Test
  void getOk() {
    final List<Exercise> expected = easyRandom.objects(Exercise.class, LIST_SIZE).toList();
    final Set<UUID> exercisesIds =
        expected.stream().map(BaseEntity::getId).collect(Collectors.toSet());
    final UUID userId = UUID.randomUUID();
    expected.stream()
        .filter(exercise -> exercise.getAccessType() == AccessTypeEnum.PRIVATE)
        .forEach(exercise -> exercise.setOwner(userId));

    when(exerciseDao.getFullExerciseByIds(exercisesIds)).thenReturn(expected);
    doNothing().when(authExercisesService).checkAccess(expected, AuthOperations.READ);

    final List<GetExercisesByIdsResponseApplication> result =
        getExercisesByIdsService.execute(exercisesIds);
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result)
        .usingRecursiveComparison()
        .ignoringFields("muscleGroups")
        .isEqualTo(expected);
  }

  @Test
  void getNotPermission() {
    final List<Exercise> exercises = easyRandom.objects(Exercise.class, LIST_SIZE).toList();
    final Set<UUID> exercisesIds =
        exercises.stream().map(BaseEntity::getId).collect(Collectors.toSet());
    final AuthOperations readOperation = AuthOperations.READ;
    final UUID userId = UUID.randomUUID();
    exercises.stream()
        .filter(exercise -> exercise.getAccessType() == AccessTypeEnum.PRIVATE)
        .forEach(exercise -> exercise.setOwner(userId));
    final Exercise exerciseNotPermission = exercises.get(3);
    exerciseNotPermission.setAccessType(AccessTypeEnum.PRIVATE);
    exerciseNotPermission.setOwner(UUID.randomUUID());

    when(exerciseDao.getFullExerciseByIds(exercisesIds)).thenReturn(exercises);
    doThrow(new IllegalAccessException(exerciseNotPermission, readOperation, userId))
        .when(authExercisesService)
        .checkAccess(exercises, readOperation);

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class, () -> getExercisesByIdsService.execute(exercisesIds));
    assertEquals(Exercise.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(exerciseNotPermission.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(readOperation, exception.getAuthOperations());
  }
}
