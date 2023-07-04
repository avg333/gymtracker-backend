package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.GetExerciseByIdService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.model.GetExerciseByIdResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.mapper.GetExerciseByIdControllerMapperImpl;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.model.GetExerciseByIdResponseInfrastructure;
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
class GetExerciseByIdControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetExerciseByIdControllerImpl getExerciseByIdController;

  @Mock private GetExerciseByIdService getExerciseByIdService;
  @Spy private GetExerciseByIdControllerMapperImpl getExerciseByIdControllerMapper;

  @Test
  void get() {
    final UUID exerciseId = UUID.randomUUID();
    final GetExerciseByIdResponseApplication expected =
        easyRandom.nextObject(GetExerciseByIdResponseApplication.class);

    when(getExerciseByIdService.execute(exerciseId)).thenReturn(expected);

    final ResponseEntity<GetExerciseByIdResponseInfrastructure> result =
        getExerciseByIdController.execute(exerciseId);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(expected.getId(), result.getBody().getId());
    assertEquals(expected.getName(), result.getBody().getName());
    assertEquals(expected.getDescription(), result.getBody().getDescription());
    assertEquals(expected.isUnilateral(), result.getBody().isUnilateral());

    assertEquals(expected.getLoadType().getId(), result.getBody().getLoadType().getId());
    assertEquals(expected.getLoadType().getName(), result.getBody().getLoadType().getName());
    assertEquals(
        expected.getLoadType().getDescription(), result.getBody().getLoadType().getDescription());

    assertEquals(
        expected.getMuscleSubGroups().size(), result.getBody().getMuscleSubGroups().size());
    for (int j = 0; j < expected.getMuscleSubGroups().size(); j++) {
      final var msubgExpected = expected.getMuscleSubGroups().stream().toList().get(j);
      final var musbgResult = result.getBody().getMuscleSubGroups().get(j);
      assertEquals(msubgExpected.getId(), musbgResult.getId());
      assertEquals(msubgExpected.getName(), musbgResult.getName());
      assertEquals(msubgExpected.getDescription(), musbgResult.getDescription());
    }

    assertEquals(
        expected.getMuscleGroupExercises().size(),
        result.getBody().getMuscleGroupExercises().size());
    for (int j = 0; j < expected.getMuscleGroupExercises().size(); j++) {
      final var mgExExpected = expected.getMuscleGroupExercises().stream().toList().get(j);
      final var mgExesult = result.getBody().getMuscleGroupExercises().get(j);
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
