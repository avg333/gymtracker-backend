package org.avillar.gymtracker.exercisesapi.getallmusclegroupsbymusclesupgroup.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.GetAllMuscleGroupsByMuscleSupGroupService;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.model.GetAllMuscleGroupsByMuscleSupGroupResponseApplication;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.GetAllMuscleGroupsByMuscleSupGroupControllerImpl;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.mapper.GetAllMuscleGroupsByMuscleSupGroupControllerMapperImpl;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.model.GetAllMuscleGroupsByMuscleSupGroupResponse;
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

    final ResponseEntity<List<GetAllMuscleGroupsByMuscleSupGroupResponse>> result =
        getAllMuscleGroupsByMuscleSupGroupControllerImpl.execute(muscleSupGroupId);
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody()).hasSameSizeAs(expected);
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
