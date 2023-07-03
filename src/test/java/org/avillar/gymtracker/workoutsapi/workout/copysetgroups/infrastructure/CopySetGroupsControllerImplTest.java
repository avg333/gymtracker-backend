package org.avillar.gymtracker.workoutsapi.workout.copysetgroups.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.CopySetGroupsService;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.model.CopySetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.model.CopySetGroupsResponseApplication.Set;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.CopySetGroupsControllerImpl;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.mapper.CopySetGroupsControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsRequestInfrastructure.Source;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsResponseInfrastructure;
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
class CopySetGroupsControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CopySetGroupsControllerImpl updateWorkoutSetGroupsController;

  @Mock private CopySetGroupsService copySetGroupsService;
  @Spy private CopySetGroupsControllerMapperImpl updateWorkoutSetGroupsControllerMapper;

  @Test
  void copySetGroups() {
    final UUID workoutDestinationId = UUID.randomUUID();
    final CopySetGroupsRequestInfrastructure request =
        new CopySetGroupsRequestInfrastructure();
    request.setId(UUID.randomUUID());
    request.setSource(Source.WORKOUT);
    final List<CopySetGroupsResponseApplication> expected =
        easyRandom.objects(CopySetGroupsResponseApplication.class, 5).toList();

    when(copySetGroupsService.execute(
            workoutDestinationId, request.getId(), request.getSource() == Source.WORKOUT))
        .thenReturn(expected);

    final ResponseEntity<List<CopySetGroupsResponseInfrastructure>> result =
        updateWorkoutSetGroupsController.execute(workoutDestinationId, request);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(expected.size(), result.getBody().size());
    assertEquals(expected.get(0).getId(), result.getBody().get(0).getId());
    assertEquals(expected.get(0).getDescription(), result.getBody().get(0).getDescription());
    assertEquals(expected.get(0).getListOrder(), result.getBody().get(0).getListOrder());
    assertEquals(expected.get(0).getExerciseId(), result.getBody().get(0).getExerciseId());
    assertEquals(expected.get(0).getSets().size(), result.getBody().get(0).getSets().size());
    final Set setExpected = expected.get(0).getSets().get(0);
    final CopySetGroupsResponseInfrastructure.Set setResult =
        result.getBody().get(0).getSets().get(0);
    assertEquals(setExpected.getId(), setResult.getId());
    assertEquals(setExpected.getDescription(), setResult.getDescription());
    assertEquals(setExpected.getListOrder(), setResult.getListOrder());
    assertEquals(setExpected.getWeight(), setResult.getWeight());
    assertEquals(setExpected.getRir(), setResult.getRir());
    assertEquals(setExpected.getReps(), setResult.getReps());
  }
}
