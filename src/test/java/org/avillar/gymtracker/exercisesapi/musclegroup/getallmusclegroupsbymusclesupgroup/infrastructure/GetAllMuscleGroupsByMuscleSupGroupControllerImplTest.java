package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.GetAllMuscleGroupsByMuscleSupGroupService;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.model.GetAllMuscleGroupsByMuscleSupGroupResponseApplication;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.mapper.GetAllMuscleGroupsByMuscleSupGroupControllerMapperImpl;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.model.GetAllMuscleGroupsByMuscleSupGroupResponseInfrastructure;
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
class GetAllMuscleGroupsByMuscleSupGroupControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks
  private GetAllMuscleGroupsByMuscleSupGroupControllerImpl
      getAllMuscleGroupsByMuscleSupGroupControllerImpl;

  @Mock private GetAllMuscleGroupsByMuscleSupGroupService getAllMuscleGroupsByMuscleSupGroupService;

  @Spy
  private GetAllMuscleGroupsByMuscleSupGroupControllerMapperImpl
      getAllMuscleGroupsByMuscleSupGroupControllerMapper;

  @Test
  void get() {
    final UUID muscleSupGroupId = UUID.randomUUID();
    final List<GetAllMuscleGroupsByMuscleSupGroupResponseApplication> expected =
        easyRandom.objects(GetAllMuscleGroupsByMuscleSupGroupResponseApplication.class, 2).toList();

    when(getAllMuscleGroupsByMuscleSupGroupService.execute(muscleSupGroupId)).thenReturn(expected);

    final ResponseEntity<List<GetAllMuscleGroupsByMuscleSupGroupResponseInfrastructure>> result =
        getAllMuscleGroupsByMuscleSupGroupControllerImpl.execute(muscleSupGroupId);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(expected.size(), result.getBody().size());
    for (int i = 0; i < expected.size(); i++) {
      final var mgExpected = expected.get(i);
      final var mgResult = result.getBody().get(i);
      assertEquals(mgExpected.getId(), mgResult.getId());
      assertEquals(mgExpected.getName(), mgResult.getName());
      assertEquals(mgExpected.getDescription(), mgResult.getDescription());
      assertEquals(mgExpected.getMuscleSubGroups().size(), mgResult.getMuscleSubGroups().size());
      for (int j = 0; j < mgExpected.getMuscleSubGroups().size(); j++) {
        final var msgExpected = mgExpected.getMuscleSubGroups().get(j);
        final var msgResult = mgResult.getMuscleSubGroups().get(j);
        assertEquals(msgExpected.getId(), msgResult.getId());
        assertEquals(msgExpected.getName(), msgResult.getName());
        assertEquals(msgExpected.getDescription(), msgResult.getDescription());
      }
    }
  }
}
