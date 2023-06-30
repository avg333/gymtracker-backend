package org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.GetMuscleSupGroupService;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.model.GetAllMuscleSupGroupsResponseApplication;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.model.GetAllMuscleSupGroupsResponseApplication.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.model.GetAllMuscleSupGroupsResponseApplication.MuscleGroup.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure.GetAllMuscleSupGroupsControllerImpl;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure.mapper.GetAllMuscleSupGroupsControllerMapperImpl;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure.model.GetAllMuscleSupGroupsResponseInfrastructure;
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
class GetAllMuscleSupGroupsControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetAllMuscleSupGroupsControllerImpl getAllMuscleSupGroupsControllerImpl;
  @Mock private GetMuscleSupGroupService getMuscleSupGroupService;
  @Spy private GetAllMuscleSupGroupsControllerMapperImpl getMuscleSupGroupControllerMapper;

  @Test
  void get() {
    final List<GetAllMuscleSupGroupsResponseApplication> expected =
        easyRandom.objects(GetAllMuscleSupGroupsResponseApplication.class, 2).toList();

    when(getMuscleSupGroupService.execute()).thenReturn(expected);

    final ResponseEntity<List<GetAllMuscleSupGroupsResponseInfrastructure>> result =
        getAllMuscleSupGroupsControllerImpl.execute();
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(expected.size(), result.getBody().size());
    assertEquals(expected.get(0).getId(), result.getBody().get(0).getId());
    assertEquals(expected.get(0).getName(), result.getBody().get(0).getName());
    assertEquals(expected.get(0).getDescription(), result.getBody().get(0).getDescription());
    final List<MuscleGroup> mgExpected = expected.get(0).getMuscleGroups();
    final List<GetAllMuscleSupGroupsResponseInfrastructure.MuscleGroup> mgResult =
        result.getBody().get(0).getMuscleGroups();
    assertEquals(mgExpected.size(), mgResult.size());
    assertEquals(mgExpected.get(0).getId(), mgResult.get(0).getId());
    assertEquals(mgExpected.get(0).getName(), mgResult.get(0).getName());
    assertEquals(mgExpected.get(0).getDescription(), mgResult.get(0).getDescription());
    final List<MuscleSubGroup> msgExpected = mgExpected.get(0).getMuscleSubGroups();
    final List<GetAllMuscleSupGroupsResponseInfrastructure.MuscleGroup.MuscleSubGroup> msgResult =
        mgResult.get(0).getMuscleSubGroups();
    assertEquals(msgExpected.size(), msgResult.size());
    assertEquals(msgExpected.get(0).getId(), msgResult.get(0).getId());
    assertEquals(msgExpected.get(0).getName(), msgResult.get(0).getName());
    assertEquals(msgExpected.get(0).getDescription(), msgResult.get(0).getDescription());
  }
}
