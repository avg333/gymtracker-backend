package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.GetExercisesByFilterService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.mapper.GetExercisesByFilterControllerMapperImpl;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterRequestInfrastructure;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponseInfrastructure;
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
    final GetExercisesByFilterRequestInfrastructure request =
        easyRandom.nextObject(GetExercisesByFilterRequestInfrastructure.class);
    final List<GetExercisesByFilterResponseApplication> expected =
        easyRandom.objects(GetExercisesByFilterResponseApplication.class, 2).toList();

    when(getExercisesByFilterService.execute(getExercisesByFilterControllerMapper.map(request)))
        .thenReturn(expected);

    final ResponseEntity<List<GetExercisesByFilterResponseInfrastructure>> result =
        getExercisesByFilterController.execute(
            request.getName(),
            request.getDescription(),
            request.getUnilateral(),
            request.getLoadTypeIds(),
            request.getMuscleSupGroupIds(),
            request.getMuscleGroupIds(),
            request.getMuscleSubGroupIds());
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(expected.size(), result.getBody().size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getId(), result.getBody().get(i).getId());
      assertEquals(expected.get(i).getName(), result.getBody().get(i).getName());
      assertEquals(expected.get(i).getDescription(), result.getBody().get(i).getDescription());
      assertEquals(expected.get(i).isUnilateral(), result.getBody().get(i).isUnilateral());
    }
  }
}
