package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.mapper.GetExercisesByIdsServiceMapperImpl;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.model.GetExercisesByIdsResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetExercisesByIdsServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks GetExercisesByIdsServiceImpl getExercisesByIdsService;

  @Mock private ExerciseDao exerciseDao;
  @Mock private AuthExercisesService authExercisesService;
  @Spy private GetExercisesByIdsServiceMapperImpl getExercisesByIdsServiceMapper;

  @Test
  void getOk() {
    final Set<UUID> exercisesIds = easyRandom.objects(UUID.class, 5).collect(Collectors.toSet());
    final List<Exercise> expected =
        easyRandom.objects(Exercise.class, exercisesIds.size()).toList();
    final UUID userId = UUID.randomUUID();
    expected.stream()
        .filter(exercise -> exercise.getAccessType() == AccessTypeEnum.PRIVATE)
        .forEach(exercise -> exercise.setOwner(userId));
    int x = 0;
    for (final UUID exerciseId : exercisesIds) {
      expected.get(x++).setId(exerciseId);
    }

    when(exerciseDao.getFullExerciseByIds(exercisesIds)).thenReturn(expected);
    doNothing().when(authExercisesService).checkAccess(expected, AuthOperations.READ);

    final List<GetExercisesByIdsResponseApplication> result =
        getExercisesByIdsService.execute(exercisesIds);
    assertEquals(expected.size(), result.size());
    for (int i = 0; i < expected.size(); i++) {
      final var exerciseExpected = expected.get(i);
      final var exerciseResult = result.get(i);
      assertEquals(exerciseExpected.getId(), exerciseResult.getId());
      assertEquals(exerciseExpected.getName(), exerciseResult.getName());
      assertEquals(exerciseExpected.getDescription(), exerciseResult.getDescription());
      assertEquals(exerciseExpected.getUnilateral(), exerciseResult.isUnilateral());

      assertEquals(exerciseExpected.getLoadType().getId(), exerciseResult.getLoadType().getId());
      assertEquals(
          exerciseExpected.getLoadType().getName(), exerciseResult.getLoadType().getName());
      assertEquals(
          exerciseExpected.getLoadType().getDescription(),
          exerciseResult.getLoadType().getDescription());

      assertEquals(
          exerciseExpected.getMuscleSubGroups().size(), exerciseResult.getMuscleSubGroups().size());
      for (int j = 0; j < exerciseExpected.getMuscleSubGroups().size(); j++) {
        final var msubgExpected = exerciseExpected.getMuscleSubGroups().stream().toList().get(j);
        final var musbgResult = exerciseResult.getMuscleSubGroups().get(j);
        assertEquals(msubgExpected.getId(), musbgResult.getId());
        assertEquals(msubgExpected.getName(), musbgResult.getName());
        assertEquals(msubgExpected.getDescription(), musbgResult.getDescription());
      }

      assertEquals(
          exerciseExpected.getMuscleGroupExercises().size(),
          exerciseResult.getMuscleGroupExercises().size());
      for (int j = 0; j < exerciseExpected.getMuscleGroupExercises().size(); j++) {
        final var mgExExpected =
            exerciseExpected.getMuscleGroupExercises().stream().toList().get(j);
        final var mgExesult = exerciseResult.getMuscleGroupExercises().get(j);
        assertEquals(mgExExpected.getWeight(), mgExesult.getWeight());
        assertEquals(mgExExpected.getId(), mgExesult.getId());
        assertEquals(mgExExpected.getMuscleGroup().getId(), mgExesult.getMuscleGroup().getId());
        assertEquals(mgExExpected.getMuscleGroup().getName(), mgExesult.getMuscleGroup().getName());
        assertEquals(
            mgExExpected.getMuscleGroup().getDescription(),
            mgExesult.getMuscleGroup().getDescription());

        assertEquals(
            mgExExpected.getMuscleGroup().getMuscleSupGroups().size(),
            mgExesult.getMuscleGroup().getMuscleSupGroups().size());
        for (int k = 0; k < mgExExpected.getMuscleGroup().getMuscleSupGroups().size(); k++) {
          final var msupgExpected =
              mgExExpected.getMuscleGroup().getMuscleSupGroups().stream().toList().get(k);
          final var msupgResult = mgExesult.getMuscleGroup().getMuscleSupGroups().get(k);
          assertEquals(msupgExpected.getId(), msupgResult.getId());
          assertEquals(msupgExpected.getName(), msupgResult.getName());
          assertEquals(msupgExpected.getDescription(), msupgResult.getDescription());
        }
      }
    }
  }

  @Test
  void getNotPermission() {
    final List<Exercise> exercises = easyRandom.objects(Exercise.class, 5).toList();
    final Set<UUID> exercisesIds = easyRandom.objects(UUID.class, 5).collect(Collectors.toSet());
    final AuthOperations authOperation = AuthOperations.READ;
    final UUID userId = UUID.randomUUID();

    when(exerciseDao.getFullExerciseByIds(exercisesIds)).thenReturn(exercises);
    doThrow(new IllegalAccessException(exercises.get(0), authOperation, userId))
        .when(authExercisesService)
        .checkAccess(exercises, authOperation);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class, () -> getExercisesByIdsService.execute(exercisesIds));
    assertEquals(Exercise.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(exercises.get(0).getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperation, exception.getAuthOperations());
  }
}
