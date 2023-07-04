package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.GetExercisesByIdsService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.model.GetExercisesByIdsResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.mapper.GetExercisesByIdsControllerMapperImpl;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.model.GetExercisesByIdsResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class GetExercisesByIdsControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetExercisesByIdsControllerImpl getExercisesByIdsController;

  @Mock private GetExercisesByIdsService getExercisesByIdsService;
  @Spy private GetExercisesByIdsControllerMapperImpl getExercisesByIdsControllerMapper;

  @Test
  void get() {
    final Set<UUID> request = easyRandom.objects(UUID.class, 10).collect(Collectors.toSet());
    final List<GetExercisesByIdsResponseApplication> expected =
        easyRandom.objects(GetExercisesByIdsResponseApplication.class, 10).toList();

    when(getExercisesByIdsService.execute(request)).thenReturn(expected);

    final ResponseEntity<List<GetExercisesByIdsResponseInfrastructure>> result =
        getExercisesByIdsController.execute(request);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(expected.size(), result.getBody().size());
    for (int i = 0; i < expected.size(); i++) {
      final var exerciseExpected = expected.get(i);
      final var exerciseResult = result.getBody().get(i);
      assertEquals(exerciseExpected.getId(), exerciseResult.getId());
      assertEquals(exerciseExpected.getName(), exerciseResult.getName());
      assertEquals(exerciseExpected.getDescription(), exerciseResult.getDescription());
      assertEquals(exerciseExpected.isUnilateral(), exerciseResult.isUnilateral());

      assertEquals(exerciseExpected.getLoadType().getId(), exerciseResult.getLoadType().getId());
      assertEquals(
          exerciseExpected.getLoadType().getName(), exerciseResult.getLoadType().getName());
      assertEquals(
          exerciseExpected.getLoadType().getDescription(),
          exerciseResult.getLoadType().getDescription());

      assertEquals(
          exerciseExpected.getMuscleSubGroups().size(), exerciseResult.getMuscleSubGroups().size());
      for (int j = 0; j < exerciseExpected.getMuscleSubGroups().size(); j++) {
        final var msubgExpected = exerciseExpected.getMuscleSubGroups().get(j);
        final var musbgResult = exerciseResult.getMuscleSubGroups().get(j);
        assertEquals(msubgExpected.getId(), musbgResult.getId());
        assertEquals(msubgExpected.getName(), musbgResult.getName());
        assertEquals(msubgExpected.getDescription(), musbgResult.getDescription());
      }

      assertEquals(
          exerciseExpected.getMuscleGroupExercises().size(),
          exerciseResult.getMuscleGroupExercises().size());
      for (int j = 0; j < exerciseExpected.getMuscleGroupExercises().size(); j++) {
        final var mgExExpected = exerciseExpected.getMuscleGroupExercises().get(j);
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
          final var msupgExpected = mgExExpected.getMuscleGroup().getMuscleSupGroups().get(k);
          final var msupgResult = mgExesult.getMuscleGroup().getMuscleSupGroups().get(k);
          assertEquals(msupgExpected.getId(), msupgResult.getId());
          assertEquals(msupgExpected.getName(), msupgResult.getName());
          assertEquals(msupgExpected.getDescription(), msupgResult.getDescription());
        }
      }
    }
  }
}
