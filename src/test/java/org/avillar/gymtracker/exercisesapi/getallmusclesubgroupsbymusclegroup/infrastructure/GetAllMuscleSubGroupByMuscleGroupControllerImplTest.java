package org.avillar.gymtracker.exercisesapi.getallmusclesubgroupsbymusclegroup.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application.GetAllMuscleSubGroupByMuscleGroupService;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application.model.GetAllMuscleSubGroupByMuscleGroupResponseApplication;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure.GetAllMuscleSubGroupByMuscleGroupControllerImpl;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure.mapper.GetAllMuscleSubGroupByMuscleGroupControllerMapper;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure.model.GetAllMuscleSubGroupByMuscleGroupResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetAllMuscleSubGroupByMuscleGroupControllerImplTest {

  private static final int LIST_SIZE = 5;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks
  private GetAllMuscleSubGroupByMuscleGroupControllerImpl
      getAllMuscleSubGroupByMuscleGroupController;

  @Mock private GetAllMuscleSubGroupByMuscleGroupService getAllMuscleSubGroupByMuscleGroupService;

  @Spy
  private final GetAllMuscleSubGroupByMuscleGroupControllerMapper
      getAllMuscleSubGroupByMuscleGroupControllerMapper =
          Mappers.getMapper(GetAllMuscleSubGroupByMuscleGroupControllerMapper.class);

  @Test
  void get() {
    final UUID muscleGroupId = UUID.randomUUID();
    final List<GetAllMuscleSubGroupByMuscleGroupResponseApplication> expected =
        easyRandom
            .objects(GetAllMuscleSubGroupByMuscleGroupResponseApplication.class, LIST_SIZE)
            .toList();

    when(getAllMuscleSubGroupByMuscleGroupService.execute(muscleGroupId)).thenReturn(expected);

    final List<GetAllMuscleSubGroupByMuscleGroupResponse> result =
        getAllMuscleSubGroupByMuscleGroupController.execute(muscleGroupId);
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }
}
