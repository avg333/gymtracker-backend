package org.avillar.gymtracker.exercisesapi.getallmusclesubgroupsbymusclegroup.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSubGroupDao;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application.GetAllMuscleSubGroupByMuscleGroupServiceImpl;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application.mapper.GetAllMuscleSubGroupByMuscleGroupServiceMapper;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application.model.GetAllMuscleSubGroupByMuscleGroupResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetAllMuscleSubGroupByMuscleGroupServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks
  private GetAllMuscleSubGroupByMuscleGroupServiceImpl getAllMuscleSubGroupByMuscleGroupService;

  @Mock private MuscleSubGroupDao muscleSubGroupDao;

  @Spy
  private GetAllMuscleSubGroupByMuscleGroupServiceMapper
      getAllMuscleSubGroupByMuscleGroupServiceMapper =
          Mappers.getMapper(GetAllMuscleSubGroupByMuscleGroupServiceMapper.class);

  @Test
  void execute() {
    final UUID muscleGroupId = UUID.randomUUID();
    final List<MuscleSubGroup> expected = easyRandom.objects(MuscleSubGroup.class, 2).toList();

    when(muscleSubGroupDao.getAllByMuscleGroupId(muscleGroupId)).thenReturn(expected);

    final List<GetAllMuscleSubGroupByMuscleGroupResponseApplication> result =
        getAllMuscleSubGroupByMuscleGroupService.execute(muscleGroupId);
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }
}
