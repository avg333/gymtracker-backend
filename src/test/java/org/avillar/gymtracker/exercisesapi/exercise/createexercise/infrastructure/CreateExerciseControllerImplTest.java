package org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.CreateExerciseService;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model.CreateExerciseResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.mapper.CreteExerciseControllerMapperImpl;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseRequest;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseResponse;
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
class CreateExerciseControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CreateExerciseControllerImpl createExerciseController;

  @Mock private CreateExerciseService createExerciseService;
  @Spy private CreteExerciseControllerMapperImpl creteExerciseControllerMapper;

  @Test
  void createExerciseOk() {
    final UUID userId = UUID.randomUUID();
    final CreateExerciseResponseApplication expected =
        easyRandom.nextObject(CreateExerciseResponseApplication.class);
    final CreateExerciseRequest createExerciseRequest =
        easyRandom.nextObject(CreateExerciseRequest.class);

    when(createExerciseService.execute(
            userId, creteExerciseControllerMapper.map(createExerciseRequest)))
        .thenReturn(expected);

    final ResponseEntity<CreateExerciseResponse> result =
        createExerciseController.execute(userId, createExerciseRequest);
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody().getId()).isNotNull();
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
