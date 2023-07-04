package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.GetSetGroupsByExerciseService;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.model.GetSetGroupsByExerciseResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.mapper.GetSetGroupsByExerciseControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.model.GetSetGroupsByExerciseResponseInfrastructure;
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
class GetSetGroupsByExerciseControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetSetGroupsByExerciseControllerImpl getSetGroupsByExerciseControllerImpl;

  @Mock private GetSetGroupsByExerciseService getSetGroupsByExerciseService;
  @Spy private GetSetGroupsByExerciseControllerMapperImpl getExerciseSetGroupsControllerMapper;

  @Test
  void getOk() {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final List<GetSetGroupsByExerciseResponseApplication> expected =
        easyRandom.objects(GetSetGroupsByExerciseResponseApplication.class, 5).toList();
    expected.forEach(sg -> sg.setExerciseId(exerciseId));

    when(getSetGroupsByExerciseService.execute(userId, exerciseId)).thenReturn(expected);

    final ResponseEntity<List<GetSetGroupsByExerciseResponseInfrastructure>> result =
        getSetGroupsByExerciseControllerImpl.execute(userId, exerciseId);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(expected.size(), result.getBody().size());
    for (int i = 0; i < expected.size(); i++) {
      final var setGroupExpected = expected.get(i);
      final var setGroupResult = result.getBody().get(i);
      assertEquals(setGroupExpected.getId(), setGroupResult.getId());
      assertEquals(setGroupExpected.getDescription(), setGroupResult.getDescription());
      assertEquals(setGroupExpected.getListOrder(), setGroupResult.getListOrder());
      assertEquals(setGroupExpected.getExerciseId(), setGroupResult.getExerciseId());
      assertEquals(setGroupExpected.getWorkout().getId(), setGroupResult.getWorkout().getId());
      assertEquals(setGroupExpected.getSets().size(), setGroupResult.getSets().size());
      for (int k = 0; k < expected.size(); k++) {
        final var setExpected = setGroupExpected.getSets().get(k);
        final var setResult = setGroupResult.getSets().get(k);
        assertEquals(setExpected.getId(), setResult.getId());
        assertEquals(setExpected.getDescription(), setResult.getDescription());
        assertEquals(setExpected.getListOrder(), setResult.getListOrder());
        assertEquals(setExpected.getRir(), setResult.getRir());
        assertEquals(setExpected.getReps(), setResult.getReps());
        assertEquals(setExpected.getWeight(), setResult.getWeight());
      }
    }
  }
}
