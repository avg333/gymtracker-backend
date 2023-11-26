package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.GetAllMuscleGroupsByMuscleSupGroupService;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.mapper.GetAllMuscleGroupsByMuscleSupGroupControllerMapper;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.model.GetAllMuscleGroupsByMuscleSupGroupResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class GetAllMuscleGroupsByMuscleSupGroupControllerImplTest {

  @InjectMocks
  private GetAllMuscleGroupsByMuscleSupGroupControllerImpl
      getAllMuscleGroupsByMuscleSupGroupControllerImpl;

  @Mock private GetAllMuscleGroupsByMuscleSupGroupService getAllMuscleGroupsByMuscleSupGroupService;

  @Mock
  private GetAllMuscleGroupsByMuscleSupGroupControllerMapper
      getAllMuscleGroupsByMuscleSupGroupControllerMapper;

  @Test
  void shouldGetAllMuscleGroupsByMuscleSupGroupSuccessfully() {
    final UUID muscleSupGroupId = UUID.randomUUID();
    final List<MuscleGroup> serviceResponse = Instancio.createList(MuscleGroup.class);
    final List<GetAllMuscleGroupsByMuscleSupGroupResponse> mapperResponse =
        Instancio.createList(GetAllMuscleGroupsByMuscleSupGroupResponse.class);

    when(getAllMuscleGroupsByMuscleSupGroupService.execute(muscleSupGroupId))
        .thenReturn(serviceResponse);
    when(getAllMuscleGroupsByMuscleSupGroupControllerMapper.map(serviceResponse))
        .thenReturn(mapperResponse);

    assertThat(getAllMuscleGroupsByMuscleSupGroupControllerImpl.execute(muscleSupGroupId))
        .isEqualTo(mapperResponse);
  }
}
