package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesubgroupsbymusclegroup.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesubgroupsbymusclegroup.application.GetAllMuscleSubGroupByMuscleGroupService;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesubgroupsbymusclegroup.infrastructure.mapper.GetAllMuscleSubGroupByMuscleGroupControllerMapper;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesubgroupsbymusclegroup.infrastructure.model.GetAllMuscleSubGroupByMuscleGroupResponse;
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
class GetAllMuscleSubGroupByMuscleGroupControllerImplTest {

  @InjectMocks
  private GetAllMuscleSubGroupByMuscleGroupControllerImpl
      getAllMuscleSubGroupByMuscleGroupController;

  @Mock private GetAllMuscleSubGroupByMuscleGroupService getAllMuscleSubGroupByMuscleGroupService;

  @Mock
  private GetAllMuscleSubGroupByMuscleGroupControllerMapper
      getAllMuscleSubGroupByMuscleGroupControllerMapper;

  @Test
  void shouldGetAllMuscleSubGroupByMuscleGroupSuccessfully() {
    final UUID muscleGroupId = UUID.randomUUID();
    final List<MuscleSubGroup> serviceResponse = Instancio.createList(MuscleSubGroup.class);
    final List<GetAllMuscleSubGroupByMuscleGroupResponse> mapperResponse =
        Instancio.createList(GetAllMuscleSubGroupByMuscleGroupResponse.class);

    when(getAllMuscleSubGroupByMuscleGroupService.execute(muscleGroupId))
        .thenReturn(serviceResponse);
    when(getAllMuscleSubGroupByMuscleGroupControllerMapper.map(serviceResponse))
        .thenReturn(mapperResponse);

    assertThat(getAllMuscleSubGroupByMuscleGroupController.execute(muscleGroupId))
        .isEqualTo(mapperResponse);
  }
}
