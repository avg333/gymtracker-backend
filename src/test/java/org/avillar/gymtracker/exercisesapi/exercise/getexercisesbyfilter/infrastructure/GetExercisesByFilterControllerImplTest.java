package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.GetExercisesByFilterService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.mapper.GetExercisesByFilterControllerMapperImpl;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterRequest;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponse;
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
class GetExercisesByFilterControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetExercisesByFilterControllerImpl getExercisesByFilterController;

  @Mock private GetExercisesByFilterService getExercisesByFilterService;
  @Spy private GetExercisesByFilterControllerMapperImpl getExercisesByFilterControllerMapper;

  @Test
  void get() {
    final GetExercisesByFilterRequest request =
        easyRandom.nextObject(GetExercisesByFilterRequest.class);
    final List<GetExercisesByFilterResponseApplication> expected =
        easyRandom.objects(GetExercisesByFilterResponseApplication.class, 2).toList();

    when(getExercisesByFilterService.execute(getExercisesByFilterControllerMapper.map(request)))
        .thenReturn(expected);

    final ResponseEntity<List<GetExercisesByFilterResponse>> result =
        getExercisesByFilterController.execute(
            request.getName(),
            request.getDescription(),
            request.getUnilateral(),
            request.getLoadTypeIds(),
            request.getMuscleSupGroupIds(),
            request.getMuscleGroupIds(),
            request.getMuscleSubGroupIds());
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody()).hasSameSizeAs(expected);
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
