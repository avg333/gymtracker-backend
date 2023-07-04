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
    assertEquals(expected.getLoadType().getId(), result.getBody().getLoadType().getId());
    assertEquals(expected.getLoadType().getName(), result.getBody().getLoadType().getName());
    assertEquals(
        expected.getLoadType().getDescription(), result.getBody().getLoadType().getDescription());
    // TODO Acabar de comparar el resto de valores
  }
}
