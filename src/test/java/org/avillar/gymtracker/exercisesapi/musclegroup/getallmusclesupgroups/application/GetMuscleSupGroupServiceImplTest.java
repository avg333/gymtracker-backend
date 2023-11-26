package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesupgroups.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSupGroup;
import org.avillar.gymtracker.exercisesapi.common.facade.musclesupgroup.MuscleSupGroupFacade;
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
class GetMuscleSupGroupServiceImplTest {

  @InjectMocks private GetMuscleSupGroupServiceImpl getGetMuscleSupGroupService;

  @Mock private MuscleSupGroupFacade muscleSupGroupFacade;

  @Test
  void shouldGetAllMuscleSupGroupsSuccessfully() {
    final List<MuscleSupGroup> facadeResponse = Instancio.createList(MuscleSupGroup.class);

    when(muscleSupGroupFacade.getAllMuscleSupGroups()).thenReturn(facadeResponse);

    assertThat(getGetMuscleSupGroupService.execute()).isEqualTo(facadeResponse);
  }
}
