package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.GetSetGroupsByExerciseService;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.model.GetSetGroupsByExerciseResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.mapper.GetSetGroupsByExerciseControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.model.GetSetGroupsByExerciseResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class GetSetGroupsByExerciseControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetSetGroupsByExerciseControllerImpl getSetGroupsByExerciseControllerImpl;

  @Mock private GetSetGroupsByExerciseService getSetGroupsByExerciseService;
  @Spy private GetSetGroupsByExerciseControllerMapperImpl getExerciseSetGroupsControllerMapper;

  @Test
  void getOk() {
    final UUID userId = UUID.randomUUID();
    final List<GetSetGroupsByExerciseResponseApplication> expected =
        easyRandom.objects(GetSetGroupsByExerciseResponseApplication.class, 5).toList();

    when(getSetGroupsByExerciseService.execute(userId, expected.get(0).getExerciseId()))
        .thenReturn(expected);

    final ResponseEntity<List<GetSetGroupsByExerciseResponseInfrastructure>> result =
        getSetGroupsByExerciseControllerImpl.execute(userId, expected.get(0).getExerciseId());
    Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    Assertions.assertNotNull(result.getBody());
    Assertions.assertEquals(expected.size(), result.getBody().size());
    Assertions.assertEquals(expected.get(0).getId(), result.getBody().get(0).getId());
    Assertions.assertEquals(
        expected.get(0).getDescription(), result.getBody().get(0).getDescription());
    Assertions.assertEquals(expected.get(0).getListOrder(), result.getBody().get(0).getListOrder());
    Assertions.assertEquals(
        expected.get(0).getExerciseId(), result.getBody().get(0).getExerciseId());
    Assertions.assertEquals(
        expected.get(0).getWorkout().getId(), result.getBody().get(0).getWorkout().getId());
  }
}
