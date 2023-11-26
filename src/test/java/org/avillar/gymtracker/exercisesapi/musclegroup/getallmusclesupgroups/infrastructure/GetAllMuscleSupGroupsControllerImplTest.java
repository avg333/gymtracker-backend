package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesupgroups.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSupGroup;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesupgroups.application.GetMuscleSupGroupService;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesupgroups.infrastructure.mapper.GetAllMuscleSupGroupsControllerMapper;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesupgroups.infrastructure.model.GetAllMuscleSupGroupsResponse;
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
class GetAllMuscleSupGroupsControllerImplTest {

  @InjectMocks private GetAllMuscleSupGroupsControllerImpl getAllMuscleSupGroupsControllerImpl;

  @Mock private GetMuscleSupGroupService getMuscleSupGroupService;
  @Mock private GetAllMuscleSupGroupsControllerMapper getMuscleSupGroupControllerMapper;

  @Test
  void shouldGetAllMuscleSupGroupsSuccessfully() {
    final List<MuscleSupGroup> serviceResponse = Instancio.createList(MuscleSupGroup.class);
    final List<GetAllMuscleSupGroupsResponse> mapperResponse =
        Instancio.createList(GetAllMuscleSupGroupsResponse.class);

    when(getMuscleSupGroupService.execute()).thenReturn(serviceResponse);
    when(getMuscleSupGroupControllerMapper.map(serviceResponse)).thenReturn(mapperResponse);

    assertThat(getAllMuscleSupGroupsControllerImpl.execute()).hasSameSizeAs(mapperResponse);
  }
}
