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
      assertEquals(expected.get(i).getId(), result.getBody().get(i).getId());
      assertEquals(expected.get(i).getName(), result.getBody().get(i).getName());
      assertEquals(expected.get(i).getDescription(), result.getBody().get(i).getDescription());
      // TODO Acabar de comparar el resto de valores
    }
  }
}
