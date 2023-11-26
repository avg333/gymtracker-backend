package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.common.facade.musclegroup.MuscleGroupFacade;
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
class GetAllMuscleGroupsByMuscleSupGroupServiceImplTest {

  @InjectMocks
  private GetAllMuscleGroupsByMuscleSupGroupServiceImpl getAllMuscleGroupsByMuscleSupGroupService;

  @Mock private MuscleGroupFacade muscleGroupFacade;

  @Test
  void shouldGetAllMuscleGroupsByMuscleSupGroupSuccessfully() {
    final UUID muscleSupGroupId = UUID.randomUUID();
    final List<MuscleGroup> facadeResponse = Instancio.createList(MuscleGroup.class);

    when(muscleGroupFacade.getAllMuscleGroupsByMuscleSupGroupId(muscleSupGroupId))
        .thenReturn(facadeResponse);

    assertThat(getAllMuscleGroupsByMuscleSupGroupService.execute(muscleSupGroupId))
        .isEqualTo(facadeResponse);
  }
}
