package org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.CreateExerciseService;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model.CreateExerciseRequestApplication;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model.CreateExerciseResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.mapper.CreteExerciseControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseRequest;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateExerciseControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CreateExerciseControllerImpl createExerciseController;

  @Mock private CreateExerciseService createExerciseService;

  @Spy
  private CreteExerciseControllerMapper creteExerciseControllerMapper =
      Mappers.getMapper(CreteExerciseControllerMapper.class);

  @Test
  void createExerciseOk() {
    final UUID userId = UUID.randomUUID();
    final CreateExerciseResponseApplication expected =
        easyRandom.nextObject(CreateExerciseResponseApplication.class);
    final CreateExerciseRequest request = easyRandom.nextObject(CreateExerciseRequest.class);

    final ArgumentCaptor<CreateExerciseRequestApplication> createExerciseRequestApplicationCaptor =
        ArgumentCaptor.forClass(CreateExerciseRequestApplication.class);

    when(createExerciseService.execute(
            eq(userId), createExerciseRequestApplicationCaptor.capture()))
        .thenReturn(expected);

    assertThat(createExerciseController.execute(userId, request))
        .usingRecursiveComparison()
        .isEqualTo(expected);
    assertThat(createExerciseRequestApplicationCaptor.getValue())
        .usingRecursiveComparison()
        .isEqualTo(request);
  }
}
