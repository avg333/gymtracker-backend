package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesubgroupsbymusclegroup.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.common.facade.musclesubgroup.MuscleSubGroupFacade;
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
class GetAllMuscleSubGroupByMuscleGroupServiceImplTest {

  @InjectMocks
  private GetAllMuscleSubGroupByMuscleGroupServiceImpl getAllMuscleSubGroupByMuscleGroupService;

  @Mock private MuscleSubGroupFacade muscleSubGroupFacade;

  @Test
  void shouldGetAllMuscleSubGroupByMuscleGroupSuccessfully() {
    final UUID muscleGroupId = UUID.randomUUID();
    final List<MuscleSubGroup> facadeResponse = Instancio.createList(MuscleSubGroup.class);

    when(muscleSubGroupFacade.getAllMuscleSubGroupsByMuscleGroupId(muscleGroupId))
        .thenReturn(facadeResponse);

    assertThat(getAllMuscleSubGroupByMuscleGroupService.execute(muscleGroupId))
        .isEqualTo(facadeResponse);
  }
}
