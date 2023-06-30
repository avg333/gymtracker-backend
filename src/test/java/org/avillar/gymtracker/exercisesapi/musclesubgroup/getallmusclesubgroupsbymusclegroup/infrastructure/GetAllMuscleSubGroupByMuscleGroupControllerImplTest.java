package org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application.GetAllMuscleSubGroupByMuscleGroupService;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application.model.GetAllMuscleSubGroupByMuscleGroupResponseApplication;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure.mapper.GetAllMuscleSubGroupByMuscleGroupControllerMapperImpl;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure.model.GetAllMuscleSubGroupByMuscleGroupResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class GetAllMuscleSubGroupByMuscleGroupControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks
  private GetAllMuscleSubGroupByMuscleGroupControllerImpl
      getAllMuscleSubGroupByMuscleGroupController;

  @Mock private GetAllMuscleSubGroupByMuscleGroupService getAllMuscleSubGroupByMuscleGroupService;

  @Spy
  private GetAllMuscleSubGroupByMuscleGroupControllerMapperImpl
      getAllMuscleSubGroupByMuscleGroupControllerMapper;

  @Test
  void get() {
    final UUID muscleGroupId = UUID.randomUUID();
    final List<GetAllMuscleSubGroupByMuscleGroupResponseApplication> expected =
        easyRandom.objects(GetAllMuscleSubGroupByMuscleGroupResponseApplication.class, 2).toList();

    when(getAllMuscleSubGroupByMuscleGroupService.execute(muscleGroupId)).thenReturn(expected);

    final ResponseEntity<List<GetAllMuscleSubGroupByMuscleGroupResponseInfrastructure>> result =
        getAllMuscleSubGroupByMuscleGroupController.execute(muscleGroupId);
    assertEquals(expected.size(), result.getBody().size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getId(), result.getBody().get(i).getId());
      assertEquals(expected.get(i).getName(), result.getBody().get(i).getName());
      assertEquals(expected.get(i).getDescription(), result.getBody().get(i).getDescription());
    }
  }
}
