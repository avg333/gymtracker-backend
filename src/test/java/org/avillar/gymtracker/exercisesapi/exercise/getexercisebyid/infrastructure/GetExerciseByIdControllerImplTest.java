package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.GetExerciseByIdService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.model.GetExerciseByIdResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.mapper.GetExerciseByIdControllerMapperImpl;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.model.GetExerciseByIdResponse;
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
    final GetExerciseByIdResponseApplication expected =
        easyRandom.nextObject(GetExerciseByIdResponseApplication.class);
    final UUID exerciseId = expected.getId();

    when(getExerciseByIdService.execute(exerciseId)).thenReturn(expected);

    final ResponseEntity<GetExerciseByIdResponse> result =
        getExerciseByIdController.execute(exerciseId);
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
