package org.avillar.gymtracker.exercisesapi.getallmusclesupgroups.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.GetMuscleSupGroupService;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.model.GetAllMuscleSupGroupsResponseApplication;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure.GetAllMuscleSupGroupsControllerImpl;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure.mapper.GetAllMuscleSupGroupsControllerMapper;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure.model.GetAllMuscleSupGroupsResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class GetAllMuscleSupGroupsControllerImplTest {

  private static final int LIST_SIZE = 5;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetAllMuscleSupGroupsControllerImpl getAllMuscleSupGroupsControllerImpl;

  @Mock private GetMuscleSupGroupService getMuscleSupGroupService;

  @Spy
  private final GetAllMuscleSupGroupsControllerMapper getMuscleSupGroupControllerMapper =
      Mappers.getMapper(GetAllMuscleSupGroupsControllerMapper.class);

  @Test
  void get() {
    final List<GetAllMuscleSupGroupsResponseApplication> expected =
        easyRandom.objects(GetAllMuscleSupGroupsResponseApplication.class, LIST_SIZE).toList();

    when(getMuscleSupGroupService.execute()).thenReturn(expected);

    final List<GetAllMuscleSupGroupsResponse> result =
        getAllMuscleSupGroupsControllerImpl.execute();
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }
}
